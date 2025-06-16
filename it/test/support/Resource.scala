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

package support

import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}

import scala.concurrent.duration.{Duration, MILLISECONDS, SECONDS}
import scala.concurrent.{Await, ExecutionContext, Future}

object Http {

  def get(url: String)(implicit hc: HeaderCarrier, ec: ExecutionContext, ws: WSClient): HttpResponse = perform(url) {
    request =>
      request.get()
  }

  def post(url: String, body: String, headers: Seq[(String, String)] = Seq.empty)(implicit
    hc: HeaderCarrier,
    ec: ExecutionContext,
    ws: WSClient
  ): HttpResponse = perform(url) { request =>
    request.withHttpHeaders(headers: _*).post(body)
  }

  def postEmpty(url: String)(implicit hc: HeaderCarrier, ec: ExecutionContext, ws: WSClient): HttpResponse =
    perform(url) { request =>
      request.execute("POST")
    }

  def putEmpty(url: String)(implicit hc: HeaderCarrier, ec: ExecutionContext, ws: WSClient): HttpResponse =
    perform(url) { request =>
      request.execute("PUT")
    }

  def delete(url: String)(implicit hc: HeaderCarrier, ec: ExecutionContext, ws: WSClient): HttpResponse = perform(url) {
    request =>
      request.delete()
  }

  private def perform(url: String)(
    fun: WSRequest => Future[WSResponse]
  )(implicit hc: HeaderCarrier, ec: ExecutionContext, ws: WSClient): HttpResponse =
    await(
      fun(
        ws.url(url)
          .withHttpHeaders(hc.headers(hc.names.explicitlyIncludedHeaders): _*)
          .withRequestTimeout(Duration(20000, MILLISECONDS))
      ).map(response => HttpResponse(response.status, response.body))
    )

  private def await[A](future: Future[A]) =
    Await.result(future, Duration(10, SECONDS))

}

class Resource(path: String, port: Int) {

  private def url = s"http://localhost:$port$path"

  def get()(implicit hc: HeaderCarrier = HeaderCarrier(), ec: ExecutionContext, ws: WSClient) =
    Http.get(url)(hc, ec, ws)

  def postAsJson(body: String)(implicit hc: HeaderCarrier = HeaderCarrier(), ec: ExecutionContext, ws: WSClient) =
    Http.post(url, body, Seq(HeaderNames.CONTENT_TYPE -> MimeTypes.JSON))(hc, ec, ws)

  def postEmpty()(implicit hc: HeaderCarrier = HeaderCarrier(), ec: ExecutionContext, ws: WSClient) =
    Http.postEmpty(url)(hc, ec, ws)

  def putEmpty()(implicit hc: HeaderCarrier = HeaderCarrier(), ec: ExecutionContext, ws: WSClient) =
    Http.putEmpty(url)(hc, ec, ws)
}
