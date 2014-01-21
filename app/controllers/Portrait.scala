package controllers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{Controller, Action}
import play.api.libs.concurrent.Akka
import play.api.Play.current
import play.Logger

object Portrait extends Controller {

  def portrait(id: Long) = Action {
    val MimeType = "image/jpg"
    val portraitPromise = Akka.future(download(id))
    Async {
      portraitPromise.map(p => Ok(p).as(MimeType))
    }
  }

  def download(id: Long) = {
    import java.net._
    import java.io._

    var in: InputStream = null
    var byteArray:Array[Byte] = null

    def fetch(id: Long, pictureType:String) = Option[Array[Byte]]{
      try {
        val url = new URL(f"http://www.kildarestreet.com/images/mpsL/$id%s.$pictureType%s")

        val connection = url.openConnection().asInstanceOf[HttpURLConnection]
        connection.setRequestMethod("GET")
        in = connection.getInputStream
        byteArray = Stream.continually(in.read).takeWhile(-1 !=).map(_.toByte).toArray

      } catch {
        case e: Exception => Logger.error(f"Can't find picture as $pictureType for id: $id", e)
      } finally {
        if (in != null) in.close
      }

      byteArray
    }

    fetch(id,"jpg").getOrElse(fetch(id,"png").get)
  }
}
