package controllers

import play._
import play.mvc._
import play.data.validation.{Required, Validation}

import models._

object Application extends Controller {

  def index = {
    println("index: calling render...")
    render()
  }

  def sayHello(@Required myName : String) {
    println("Application.sayHello")
    if(Validation.hasErrors) {
	println("validation errors")
        flash.error("Oops, please enter your name!")
        index
    }
    val userCount = User.count.asInstanceOf[Object]
    render(myName, userCount)
  }

}
