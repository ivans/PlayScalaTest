package controllers

import play._
import play.mvc._
import play.data.validation.{Required, Validation}

import models._

object Application extends Controller {

  def index = {
    println("index: calling render...")
    val frontPost = Post.find("order by postedAt desc").first
    val olderPosts = Post.find("order by postedAt desc").from(1).fetch(10);
    render(frontPost, olderPosts);
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

  def addUser(firstName : String, lastName : String, @Required email : String) {
    println("Application.addUser")
    if(Validation.hasErrors) {
		println("validation errors")
        flash.error("Enter username and email please!")
        index
    }
    val user = new User(email, null, firstName, lastName, false)
	user.saveThis()
	flash.success("Saved new user: " + user)
	println("Saved user")
    index
  }

}
