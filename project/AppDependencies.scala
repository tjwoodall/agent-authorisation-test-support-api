import play.core.PlayVersion
import play.sbt.PlayImport.ws
import sbt._

object AppDependencies {

  private val bootstrapVersion: String = "7.22.0"

  lazy val compileDeps = Seq(
    ws,
    "uk.gov.hmrc" %% "bootstrap-backend-play-28" % bootstrapVersion,
    "uk.gov.hmrc" %% "agent-mtd-identifiers" % "1.15.0",
    "uk.gov.hmrc" %% "agent-kenshoo-monitoring" % "5.5.0",
    "uk.gov.hmrc" %% "play-hmrc-api" % "7.2.0-play-28",
    "com.github.blemale" %% "scaffeine" % "5.2.1",
    ws
  )

  def testDeps(scope: String) = Seq(
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % scope,
    "org.scalatestplus" %% "mockito-3-12" % "3.2.10.0" % scope,
    "uk.gov.hmrc" %% "bootstrap-test-play-28" % bootstrapVersion % scope,
    "com.github.tomakehurst" % "wiremock-jre8" % "2.26.1" % scope,
    "org.pegdown" % "pegdown" % "1.6.0" % scope,
    "uk.gov.hmrc" %% "play-hal" % "3.4.0-play-28" % scope,
    "com.typesafe.play" %% "play-test" % PlayVersion.current % scope
  )

}
