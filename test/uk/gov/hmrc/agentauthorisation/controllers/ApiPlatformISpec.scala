/*
 * Copyright 2019 HM Revenue & Customs
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

import org.scalatestplus.play.OneServerPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient
import uk.gov.hmrc.agentauthorisation.stubs.DataStreamStubs
import uk.gov.hmrc.agentauthorisation.support.{Resource, WireMockSupport}
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}
import uk.gov.hmrc.play.test.UnitSpec

import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global

class ApiPlatformISpec extends UnitSpec with OneServerPerSuite with WireMockSupport with DataStreamStubs {

  override implicit lazy val app: Application = appBuilder.build()

  implicit val hc: HeaderCarrier = HeaderCarrier(otherHeaders = Seq("Accept" -> s"application/vnd.hmrc.1.0+json"))

  protected def appBuilder: GuiceApplicationBuilder =
    new GuiceApplicationBuilder()
      .configure(
        "auditing.enabled"                                 -> true,
        "auditing.consumer.baseUri.host"                   -> wireMockHost,
        "auditing.consumer.baseUri.port"                   -> wireMockPort,
        "microservice.services.agents-external-stubs.host" -> wireMockHost,
        "microservice.services.agents-external-stubs.port" -> wireMockPort,
        "microservice.services.service-locator.port"       -> wireMockPort,
        "microservice.services.service-locator.host"       -> wireMockHost,
        "microservice.services.service-locator.enabled"    -> false
      )

  implicit val ws: WSClient = app.injector.instanceOf[WSClient]

  def commonStubs(): Unit =
    givenAuditConnector()

  "/public/api/definition" should {
    "return the definition JSON" in {
      val response: HttpResponse = new Resource(s"/api/definition", port).get()
      response.status shouldBe 200

      val definition = response.json

      (definition \ "api" \ "name").as[String] shouldBe "Agent Authorisation Test Support"
      (definition \ "api" \ "categories").as[Seq[String]] should contain("AGENTS")

      val accessConfig = definition \ "api" \ "versions" \\ "access"
      (accessConfig.head \ "type").as[String] shouldBe "PRIVATE"
      (accessConfig.head \ "whitelistedApplicationIds").head.as[String] shouldBe "00010002-0003-0004-0005-000600070008"
      (accessConfig.head \ "whitelistedApplicationIds")(1).as[String] shouldBe "00090002-0003-0004-0005-000600070008"
    }
  }

  "provide RAML documentation exists for all API versions" in new ApiTestSupport {

    lazy override val runningPort: Int = port

    forAllApiVersions(ramlByVersion) {
      case (version, raml) =>
        info(s"Checking API RAML documentation for version[$version] of the API")

        withClue("RAML does not contain a valid RAML 1.0 version header") {
          raml should include("#%RAML 1.0")
        }

        withClue("RAML does not contain the title 'Agent Authorisation API'") {
          raml should include("title: Agent Authorisation")

        }

        withClue(s"RAML does not contain a matching version declaration of [$version]") {
          raml should include(s"version: $version")
        }
    }
  }
}
