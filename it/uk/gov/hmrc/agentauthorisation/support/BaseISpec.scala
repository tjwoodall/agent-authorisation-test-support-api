package uk.gov.hmrc.agentauthorisation.support

import akka.stream.Materializer
import org.scalatestplus.play.OneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.agentauthorisation.stubs.DataStreamStubs
import uk.gov.hmrc.play.test.UnitSpec

abstract class BaseISpec extends UnitSpec with OneAppPerSuite with WireMockSupport with DataStreamStubs {

  override implicit lazy val app: Application = appBuilder.build()

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

  protected implicit val materializer: Materializer = app.materializer

  def commonStubs(): Unit =
    givenAuditConnector()

  override protected def beforeEach(): Unit =
    super.beforeEach()
}
