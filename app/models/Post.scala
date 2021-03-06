package models
 
import java.util._
import javax.persistence._
import play.db.jpa._
import play.data.validation._

import scala.collection.JavaConversions._

@Entity
class Post (

	@Required
	var title : String,
	@Required
	var postedAt : Date,
	@Lob 
	@Required
	@MaxSize(10000)
	var content : String,
	@Required
	@ManyToOne(cascade=Array(CascadeType.ALL))
	var author : User,
	@OneToMany(mappedBy="post", cascade=Array(CascadeType.ALL))
	var comments : List[Comment] = new ArrayList

) extends Model {
	
	@ManyToMany(cascade=Array(CascadeType.PERSIST))
	var tags : Set[Tag] = new TreeSet[Tag]
	
	def this(author : User, title : String, content : String) {
		this(title, new Date, content, author, new ArrayList)
	}

	def previous = Post.find("postedAt < ? order by postedAt desc", postedAt).first
 
	def next = Post.find("postedAt > ? order by postedAt asc", postedAt).first
	
	def addComment(newComment : Comment) : Post = {
		comments add newComment
		this
	}

	def addComment(author : String, content : String) : Post = {
		addComment(new Comment(this, author, content))
		this
	}

	def tagItWith(name : String) : Post = {
		tags.add(Tag findOrCreateByName name)
		this
	}
	
	def isTaggedWith(name : String) = tags.exists(name equals _.name)
	
	override def toString = title
}

object Post extends QueryOn[Post] {
	
	def findTaggedWith(tag : String) : scala.List[Post] = {
		Post.find("select distinct p from Post p join p.tags as t where t.name = ?", tag).fetch
	}

}