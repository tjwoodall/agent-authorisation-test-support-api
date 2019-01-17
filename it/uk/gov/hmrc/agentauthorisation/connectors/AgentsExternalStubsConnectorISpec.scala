package uk.gov.hmrc.agentauthorisation.connectors

import uk.gov.hmrc.agentauthorisation.support.BaseISpec
import uk.gov.hmrc.http.HeaderCarrier

class AgentsExternalStubsConnectorISpec extends BaseISpec {

  val connector: AgentsExternalStubsConnector = app.injector.instanceOf[AgentsExternalStubsConnector]

  implicit val hc: HeaderCarrier = HeaderCarrier()


}
