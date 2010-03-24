package models
 
import java.util._
import javax.persistence._
import play.db.jpa._
import play.data.validation._

@Entity
class Tag(var name : String) extends Model with Comparable[Tag] {
	override def compareTo(otherTag : Tag) = name.compareTo(otherTag.name)
	override def toString = name
}

object Tag extends QueryOn[Tag]