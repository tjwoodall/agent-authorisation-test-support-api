/*
 * Copyright 2025 HM Revenue & Customs
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

import uk.gov.hmrc.domain.{SimpleObjectReads, SimpleObjectWrites, TaxIdentifier}

case class Vrn(value: String) extends TaxIdentifier {
  require(Vrn.isValid(value), s"$value is not a valid VRN.")
}

object Vrn {
  implicit val vrnReads: SimpleObjectReads[Vrn] = new SimpleObjectReads[Vrn]("value", Vrn.apply)
  implicit val vrnWrites: SimpleObjectWrites[Vrn] = new SimpleObjectWrites[Vrn](_.value)

  private[models] def regexCheck(vrn: String): Boolean = vrn.matches("[0-9]{9}")

  def isValid(vrn: String): Boolean = regexCheck(vrn)
}
