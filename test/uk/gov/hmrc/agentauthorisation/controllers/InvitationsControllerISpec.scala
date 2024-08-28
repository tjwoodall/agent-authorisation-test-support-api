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

package uk.gov.hmrc.agentauthorisation.controllers

import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.agentauthorisation.stubs.{ACAStubs, AgentsExternalStubs, TestIdentifiers}
import uk.gov.hmrc.agentauthorisation.support.BaseISpec

class InvitationsControllerISpec extends BaseISpec with ACAStubs with AgentsExternalStubs with TestIdentifiers {

  val controller: InvitationsController = app.injector.instanceOf[InvitationsController]

  val fakeRequest: FakeRequest[AnyContentAsEmpty.type] =
    FakeRequest().withHeaders("Accept" -> s"application/vnd.hmrc.1.0+json")

  "PUT /agent-authorisation-test-support/invitations/:id" when {

    "the regime type is Income Tax (main agent)" should {

      "return 204 for successfully accepting an invitation" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Pending")
        givenAcceptInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 204)
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 204 for successfully accepting an invitation (alt ITSA)" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Pending", altItsa = true)
        givenUserIdForNino(nino)
        givenAcceptInvitationStub(invitationIdITSA, nino, identifierAltITSA.toUpperCase, 204)
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 204 for accepting already accepted invitation" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Accepted")
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 409 for accepting already rejected invitation" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Rejected")
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 409 for accepting already expired invitation" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Expired")
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 404 for unable to find invitation to accept" in {
        givenITSAUserAuthenticatedInStubs()
        givenInvitationNotFound(arn, invitationIdITSA)
        givenAcceptInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 404)
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 404
      }

      "return 403 for unauthorised to accept invitation" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Pending")
        givenAcceptInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 403)
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 403
      }
    }

    "the regime type is Income Tax (supporting agent)" should {

      "return 204 for successfully accepting an invitation" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Pending")
        givenAcceptInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 204)
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 204 for successfully accepting an invitation (alt ITSA)" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Pending", altItsa = true)
        givenUserIdForNino(nino)
        givenAcceptInvitationStub(invitationIdITSA, nino, identifierAltITSA.toUpperCase, 204)
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 204 for accepting already accepted invitation" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Accepted")
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 409 for accepting already rejected invitation" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Rejected")
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 409 for accepting already expired invitation" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Expired")
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 404 for unable to find invitation to accept" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenInvitationNotFound(arn, invitationIdITSA)
        givenAcceptInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 404)
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 404
      }

      "return 403 for unauthorised to accept invitation" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Pending")
        givenAcceptInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 403)
        val result = controller.acceptInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 403
      }
    }

    "the regime type is VAT" should {

      "return 204 for successfully accepting an invitation" in {
        givenVATUserAuthenticatedInStubs()
        givenGetVATInvitationStub(arn, "Pending")
        givenAcceptInvitationStub(invitationIdVAT, validVrn.value, "VRN", 204)
        val result = controller.acceptInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 204 for accepting already accepted invitation" in {
        givenVATUserAuthenticatedInStubs()
        givenGetVATInvitationStub(arn, "Accepted")
        val result = controller.acceptInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 409 for accepting already rejected invitation" in {
        givenVATUserAuthenticatedInStubs()
        givenGetVATInvitationStub(arn, "Rejected")
        val result = controller.acceptInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 409 for accepting already expired invitation" in {
        givenVATUserAuthenticatedInStubs()
        givenGetVATInvitationStub(arn, "Expired")
        val result = controller.acceptInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 404 for unable to find invitation to accept" in {
        givenVATUserAuthenticatedInStubs()
        givenInvitationNotFound(arn, invitationIdVAT)
        givenAcceptInvitationStub(invitationIdVAT, validVrn.value, "VRN", 404)
        val result = controller.acceptInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 404
      }

      "return 403 for unauthorised to accept invitation" in {
        givenVATUserAuthenticatedInStubs()
        givenGetVATInvitationStub(arn, "Pending")
        givenAcceptInvitationStub(invitationIdVAT, validVrn.value, "VRN", 403)
        val result = controller.acceptInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 403
      }
    }

    "the regime type is not supported" should {

      "throw an exception detailing that there was an unsupported service type" in {
        givenUnsupportedRegimeUserInStubs()
        givenGetUnsupportedInvitationStub(arn, "Pending")
        val result = controller.acceptInvitation(invalidInvitationId)(fakeRequest)
        intercept[Exception](status(result)).getMessage shouldBe "Unsupported service type"
      }
    }
  }

  "DELETE /agent-authorisation-test-support/invitations/:id" when {

    "the regime type is Income Tax (main agent)" should {

      "return 204 for successfully rejecting an invitation" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Pending")
        givenRejectInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 204)
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 204 for successfully rejecting an invitation (alt ITSA)" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Pending", altItsa = true)
        givenUserIdForNino(nino)
        givenRejectInvitationStub(invitationIdITSA, nino, identifierAltITSA.toUpperCase, 204)
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 204 for accepting already rejected invitation" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Rejected")
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 409 for accepting already accepted invitation" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Accepted")
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 409 for accepting already expired invitation" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Expired")
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 404 for unable to find invitation to reject" in {
        givenITSAUserAuthenticatedInStubs()
        givenInvitationNotFound(arn, invitationIdITSA)
        givenRejectInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 404)
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 404
      }

      "return 403 for unauthorised to reject invitation" in {
        givenITSAUserAuthenticatedInStubs()
        givenGetITSAInvitationStub(arn, "Pending")
        givenRejectInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 403)
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 403
      }
    }

    "the regime type is Income Tax (supporting agent)" should {

      "return 204 for successfully rejecting an invitation" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Pending")
        givenRejectInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 204)
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 204 for successfully rejecting an invitation (alt ITSA)" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Pending", altItsa = true)
        givenUserIdForNino(nino)
        givenRejectInvitationStub(invitationIdITSA, nino, identifierAltITSA.toUpperCase, 204)
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 204 for accepting already rejected invitation" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Rejected")
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 409 for accepting already accepted invitation" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Accepted")
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 409 for accepting already expired invitation" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Expired")
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 404 for unable to find invitation to reject" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenInvitationNotFound(arn, invitationIdITSA)
        givenRejectInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 404)
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 404
      }

      "return 403 for unauthorised to reject invitation" in {
        givenITSASuppUserAuthenticatedInStubs()
        givenGetITSASuppInvitationStub(arn, "Pending")
        givenRejectInvitationStub(invitationIdITSA, mtdItId.value, identifierITSA, 403)
        val result = controller.rejectInvitation(invitationIdITSA)(fakeRequest)
        status(result) shouldBe 403
      }
    }

    "the regime type is VAT" should {

      "return 204 for successfully rejecting an invitation" in {
        givenVATUserAuthenticatedInStubs()
        givenGetVATInvitationStub(arn, "Pending")
        givenRejectInvitationStub(invitationIdVAT, validVrn.value, "VRN", 204)
        val result = controller.rejectInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 204 for accepting already rejected invitation" in {
        givenVATUserAuthenticatedInStubs()
        givenGetVATInvitationStub(arn, "Rejected")
        val result = controller.rejectInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 204
      }

      "return 409 for accepting already accepted invitation" in {
        givenVATUserAuthenticatedInStubs()
        givenGetVATInvitationStub(arn, "Accepted")
        val result = controller.rejectInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 409 for accepting already expired invitation" in {
        givenVATUserAuthenticatedInStubs()
        givenGetVATInvitationStub(arn, "Expired")
        val result = controller.rejectInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 409
      }

      "return 404 for unable to find invitation to reject" in {
        givenVATUserAuthenticatedInStubs()
        givenInvitationNotFound(arn, invitationIdVAT)
        givenRejectInvitationStub(invitationIdVAT, validVrn.value, "VRN", 404)
        val result = controller.rejectInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 404
      }

      "return 403 for unauthorised to reject invitation" in {
        givenVATUserAuthenticatedInStubs()
        givenGetVATInvitationStub(arn, "Pending")
        givenRejectInvitationStub(invitationIdVAT, validVrn.value, "VRN", 403)
        val result = controller.rejectInvitation(invitationIdVAT)(fakeRequest)
        status(result) shouldBe 403
      }
    }

    "the regime type is not supported" should {

      "throw an exception detailing that there was an unsupported service type" in {
        givenUnsupportedRegimeUserInStubs()
        givenGetUnsupportedInvitationStub(arn, "Pending")
        val result = controller.rejectInvitation(invalidInvitationId)(fakeRequest)
        intercept[Exception](status(result)).getMessage shouldBe "Unsupported service type"
      }
    }
  }
}
