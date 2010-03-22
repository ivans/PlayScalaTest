package controllers

import play._
import play.mvc._
import play.data.validation.{Required, Validation}

import models._

object Application extends Controller {

	@Before
	def addDefaults() {
		renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"))
		renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"))
	}
	
	def index = {
		println("index: calling render...")
		val frontPost = Post.find("order by postedAt desc").first
		val olderPosts = Post.find("order by postedAt desc").from(1).fetch(10);
		render(frontPost, olderPosts);
	}

	def show(id : Long) {
		val post = Post findById id
		render(post)
	}
	
	def postComment(postId : Long, @Required author : String, @Required content : String) {
		val post = Post findById postId
		if (Validation.hasErrors) {
			flash error "Obavezno je unijeti autora i sadržaj!"
	    	show(postId)
	    }
    	post.addComment(author, content);
    	flash.success("Thanks for posting %s", author);
    	show(postId);
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
