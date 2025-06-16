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

package stubs

import uk.gov.hmrc.agentmtdidentifiers.model.{Arn, MtdItId, Vrn}
import uk.gov.hmrc.domain.Nino

trait TestIdentifiers {

  val arn = Arn("TARN0000001")
  val arn2 = Arn("DARN0002185")
  val validNino = Nino("AB123456A")
  val validNinoSpace = Nino("AB 12 34 56 A")
  val nino = "AB123456A"

  val mtdItId = MtdItId("ABCDEF123456789")
  val serviceITSA = "HMRC-MTD-IT"
  val serviceITSASupp = "HMRC-MTD-IT-SUPP"
  val validPostcode = "DH14EJ"
  val invitationIdITSA = "ABERULMHCKKW3"
  val identifierITSA = "MTDITID"
  val identifierAltITSA = "ni"
  val userIdITSA = "ITSAClient001"

  val invitationIdVAT = "CZTW1KY6RTAAT"
  val serviceVAT = "HMRC-MTD-VAT"
  val identifierVAT = "VRN"
  val validVrn = Vrn("101747696")
  val invalidVrn = Vrn("101747692")
  val validVatRegDate = "2007-07-07"
  val dateOfBirth = "1980-07-07"
  val validVrn9755 = Vrn("101747641")

  val invalidInvitationId = "ZTSF4OW9CCRPT"
  val unsupportedService = "HMRC-ABC-DEF"
}
