/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.agentauthorisation.connectors

import uk.gov.hmrc.agentauthorisation.models.Invitation
import uk.gov.hmrc.agentauthorisation.stubs.{ACAStubs, TestIdentifiers}
import uk.gov.hmrc.agentauthorisation.support.BaseISpec
import uk.gov.hmrc.agentmtdidentifiers.model.Arn
import uk.gov.hmrc.http.HeaderCarrier

import scala.concurrent.ExecutionContext.Implicits.global

class InvitationsConnectorISpec extends BaseISpec with ACAStubs with TestIdentifiers {

  val connector: InvitationsConnector = app.injector.instanceOf[InvitationsConnector]

  implicit val hc: HeaderCarrier = HeaderCarrier()

  val storedItsaInvitation = Invitation(
    "ABERULMHCKKW3",
    Arn("TARN0000001"),
    "personal",
    "ABCDEF123456789",
    "MTDITID",
    "HMRC-MTD-IT",
    "Pending"
  )

  val storedVatInvitation = Invitation(
    "CZTW1KY6RTAAT",
    Arn("TARN0000001"),
    "business",
    "101747696",
    "vrn",
    "HMRC-MTD-VAT",
    "Pending"
  )

  val storedInvitations = Seq(storedItsaInvitation, storedVatInvitation)

  "getInvitation" should {
    "return an ITSA invitation" in {
      givenGetITSAInvitationStub(arn, "Pending")
      val result = connector.getInvitation(invitationIdITSA).futureValue

      result.get shouldBe storedItsaInvitation
    }

    "return an VAT invitation" in {
      givenGetVATInvitationStub(arn, "Pending")
      val result = connector.getInvitation(invitationIdVAT).futureValue

      result.get shouldBe storedVatInvitation
    }

    "return no invitation" in {
      givenInvitationNotFound(arn, invitationIdITSA)
      val result = connector.getInvitation(invitationIdITSA).futureValue

      result shouldBe None
    }
  }

  "acceptInvitation" should {
    "return 204 when cancellation is successful" in {
      givenAcceptInvitationStub(invitationIdITSA, mtdItId.value, "MTDITID", 204)
      val result = connector.acceptInvitation(invitationIdITSA, mtdItId.value, "MTDITID").futureValue

      result shouldBe Some(204)
    }

    "return 404 when invitation is not found" in {
      givenAcceptInvitationStub(invitationIdITSA, mtdItId.value, "MTDITID", 404)
      val result = connector.acceptInvitation(invitationIdITSA, mtdItId.value, "MTDITID").futureValue

      result shouldBe Some(404)
    }

    "return 403 when an invitation cannot be cancelled" in {
      givenAcceptInvitationStubInvalid(invitationIdITSA, mtdItId.value, "MTDITID")
      val result = connector.acceptInvitation(invitationIdITSA, mtdItId.value, "MTDITID").futureValue

      result shouldBe Some(403)
    }

    "return None when some other response is returned" in {
      givenAcceptInvitationStub(invitationIdITSA, mtdItId.value, "MTDITID", 403)
      val result = connector.acceptInvitation(invitationIdITSA, mtdItId.value, "MTDITID").futureValue

      result shouldBe Some(403)
    }
  }

  "rejectInvitation" should {
    "return 204 when cancellation is successful" in {
      givenRejectInvitationStub(invitationIdITSA, mtdItId.value, "MTDITID", 204)
      val result = connector.rejectInvitation(invitationIdITSA, mtdItId.value, "MTDITID").futureValue

      result shouldBe Some(204)
    }

    "return 404 when invitation is not found" in {
      givenRejectInvitationStub(invitationIdITSA, mtdItId.value, "MTDITID", 404)
      val result = connector.rejectInvitation(invitationIdITSA, mtdItId.value, "MTDITID").futureValue

      result shouldBe Some(404)
    }

    "return 403 when an invitation cannot be cancelled" in {
      givenRejectInvitationStubInvalid(invitationIdITSA, mtdItId.value, "MTDITID")
      val result = connector.rejectInvitation(invitationIdITSA, mtdItId.value, "MTDITID").futureValue

      result shouldBe Some(403)
    }

    "return None when some other response is returned" in {
      givenRejectInvitationStub(invitationIdITSA, mtdItId.value, "MTDITID", 403)
      val result = connector.rejectInvitation(invitationIdITSA, mtdItId.value, "MTDITID").futureValue

      result shouldBe Some(403)
    }
  }

}
