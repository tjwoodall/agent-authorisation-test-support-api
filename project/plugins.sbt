resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
resolvers += Resolver.url("HMRC-open-artefacts-ivy", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(Resolver.ivyStylePatterns)
resolvers += Resolver.url(
  "HMRC Private Sbt Plugin Releases",
  url("https://artefacts.tax.service.gov.uk/artifactory/hmrc-sbt-plugin-releases-local"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.9")

addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "3.5.0")
addSbtPlugin("uk.gov.hmrc" % "sbt-distributables" % "2.1.0")
addSbtPlugin("uk.gov.hmrc" % "sbt-git-versioning" % "2.4.0")
addSbtPlugin("uk.gov.hmrc" % "sbt-artifactory" % "2.0.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.16")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.4")  // provides sbt command "dependencyUpdates"
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.10.0-RC1")  // provides sbt command "dependencyTree"
