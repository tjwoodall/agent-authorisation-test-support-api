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

package uk.gov.hmrc.agentauthorisation.support

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.OptionValues
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import uk.gov.hmrc.agentmtdidentifiers.model.Arn
import uk.gov.hmrc.http.HeaderCarrier
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

abstract class BaseSpec
    extends WordSpecLike with Matchers with OptionValues with ScalaFutures with GuiceOneAppPerSuite {
  implicit val sys: ActorSystem = ActorSystem("TestSystem")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val hc: HeaderCarrier = HeaderCarrier()

  val arn: Arn = Arn("TARN0000001")
}
