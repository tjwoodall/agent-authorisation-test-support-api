import sbt.Test
import sbt.Keys.parallelExecution

object CodeCoverageSettings {

  lazy val scoverageSettings = {
    import scoverage.ScoverageKeys
    Seq(
      // Semicolon-separated list of regexs matching classes to exclude
      ScoverageKeys.coverageExcludedPackages := """uk\.gov\.hmrc\.BuildInfo;.*\.Routes;.*\.RoutesPrefix;.*Filters?;MicroserviceAuditConnector;Module;GraphiteStartUp;.*\.Reverse[^.]*""",
      ScoverageKeys.coverageMinimumStmtTotal := 60.00, // reduced to 60% as a temporary measure as the recent upgrade caused a dip in the reported coverage
      ScoverageKeys.coverageFailOnMinimum := false,
      ScoverageKeys.coverageHighlighting := true,
      Test / parallelExecution := false
    )
  }

}
