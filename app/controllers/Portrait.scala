package controllers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{ Controller, Action }
import play.api.Play.current
import play.api.cache._
import play.Logger
import scala.concurrent._

object Portrait extends Controller {
  val MimeType = "image/jpg"

  def portrait(id: Long) = Action {
    Ok.sendFile(
        content = new java.io.File(f"public/images/portrait/$id.jpg"),
        fileName = _ => id.toString
    ).as(MimeType)
  }

  def download(id: Long, baseURL: String) = {

    import java.net._
    import java.io._

    var in: InputStream = null
    var byteArray: Array[Byte] = null

    def fetch(id: Long, pictureType: String) = Option[Array[Byte]] {
      try {
        val url = new URL(baseURL + f"/$id%s.$pictureType%s")

        val connection = url.openConnection().asInstanceOf[HttpURLConnection]
        connection.setRequestMethod("GET")
        in = connection.getInputStream
        byteArray = Stream.continually(in.read).takeWhile(-1 !=).map(_.toByte).toArray
        
        

        Cache.set(baseURL + id, byteArray)

      } catch {
        case e: Exception => Logger.warn(f"Can't find picture as $pictureType for id: $id", e)
      } finally {
        if (in != null) in.close
      }

      byteArray

    }

    Cache.getAs[Array[Byte]](baseURL + id) match {
      case Some(image: Array[Byte]) => image
      case None => fetch(id, "jpg").getOrElse(fetch(id, "png").getOrElse { Logger.error(f"Can't find a picture for id: $id"); null })
    }
  }
}
