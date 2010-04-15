package controllers

import play._
import play.cache._
import play.mvc._
import play.data.validation.{Required, Validation}
import play.libs._

import models._

import scala.collection.JavaConversions._
import scala.xml._

import java.text.SimpleDateFormat
import java.util.Date

object AjaxTest extends Controller {

	def index = render()
	
	val sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
	
	val rng = new scala.util.Random
	
	def getDate = renderText(sdf.format(new Date))

	def getSomeXml = renderText(
			<div>
				<span>Ovo je ({rng.nextInt(100)}) nekakav xml</span>
				<span style="color: red">crveni tekst {rng.nextInt(100)} u spanu</span>
			</div>.toString
			)

	def getPosts = {
		val postovi = Post.findAll.toList.asInstanceOf[List[Post]]
		val xml = <table>{postovi.map(p => <tr><td>{p.title}</td><td>{p.author}</td></tr> )}</table>
		renderText(xml.toString)
	}
}