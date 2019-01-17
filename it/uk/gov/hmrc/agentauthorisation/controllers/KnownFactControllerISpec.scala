package uk.gov.hmrc.agentauthorisation.controllers

import uk.gov.hmrc.agentauthorisation.support.BaseISpec

class KnownFactControllerISpec extends BaseISpec {

  lazy val controller: KnownFactController = app.injector.instanceOf[KnownFactController]


}
