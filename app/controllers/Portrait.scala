package controllers

import play.api.mvc.{Controller, Action}

object Portrait extends Controller {

  def portrait(id: Long) = Action {
    val MimeType = "image/jpg"
     Ok( download(id)).as(MimeType)
  }

  def download(id: Long) = {
    import java.net._
    import java.io._

    var in: InputStream = null
    var byteArray:Array[Byte] = null
    try {
      val url = new URL(f"http://www.kildarestreet.com/images/mpsL/$id%s.jpg")

      val connection = url.openConnection().asInstanceOf[HttpURLConnection]
      connection.setRequestMethod("GET")
      in = connection.getInputStream
      byteArray = Stream.continually(in.read).takeWhile(-1 !=).map(_.toByte).toArray

    } catch {
      case e: Exception => println(e.printStackTrace())
    } finally {
      in.close
    }

    byteArray
  }
}
