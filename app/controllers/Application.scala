package controllers

import play._
import play.cache._
import play.mvc._
import play.data.validation.{Required, Validation}
import play.libs._

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
		val randomID = Codec.UUID()
		val cloud = Tag.getCloud
	    render(post, randomID, cloud)
	}
	
	def postComment(postId : Long, 
					@Required(message="Author is required") author : String, 
					@Required(message="A message is required") content : String,
					@Required(message="Please type the code") code : String,
					randomID : String) {
		val post = Post findById postId
		validation.equals(code, Cache.get(randomID)).message("Invalid code. Please type it again");
		if (Validation.hasErrors) {
			params.flash()
			Validation.errors.toArray.foreach(println _)
			render("Application/show.html", post, randomID);
	    }
    	post.addComment(author, content);
    	flash.success("Thanks for posting %s", author);
    	show(postId);
	}

	def captcha(id : String) {
	    val captcha = Images.captcha()
	    val code = captcha getText "#E4EAFD"
	    Cache.set(id, code, "10mn");
	    renderBinary(captcha);
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

	def listTagged(tag : String) = {
		val posts = Post.findTaggedWith(tag)
    	render(tag, posts);
	}

}
