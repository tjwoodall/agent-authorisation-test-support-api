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

package uk.gov.hmrc.agentauthorisation.models

import java.time.LocalDate
import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.domain.Nino

case class User(
  userId: String,
  groupId: Option[String] = None,
  confidenceLevel: Option[Int] = None,
  credentialStrength: Option[String] = None,
  credentialRole: Option[String] = None,
  nino: Option[Nino] = None,
  assignedPrincipalEnrolments: Seq[EnrolmentKey] = Seq.empty,
  assignedDelegatedEnrolments: Seq[EnrolmentKey] = Seq.empty,
  name: Option[String] = None,
  dateOfBirth: Option[LocalDate] = None,
  planetId: Option[String] = None,
  isNonCompliant: Option[Boolean] = None,
  complianceIssues: Option[Seq[String]] = None,
  recordIds: Seq[String] = Seq.empty,
  address: Option[User.Address] = None,
  //additionalInformation: Option[AdditionalInformation] = None,
  strideRoles: Seq[String] = Seq.empty
)

object User {

  case class Address(
    line1: Option[String] = None,
    line2: Option[String] = None,
    line3: Option[String] = None,
    line4: Option[String] = None,
    postcode: Option[String] = None,
    countryCode: Option[String] = None)

  implicit val format3: Format[Address] = Json.format[Address]
  implicit val formats: Format[User] = Json.format
}
