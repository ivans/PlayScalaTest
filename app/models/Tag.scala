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

object Tag extends QueryOn[Tag] {
	
	def findOrCreateByName(name : String) : Tag = {
		Tag.find("byName", name).first match {
			case null => new Tag(name)
			case tag => tag
		}
	}

	def getCloud() : List[Map[String, Long]] = {
		find("select new map(t.name as tag, count(p.id) as pound) from Post p join p.tags as t group by t.name")
			.fetch
			.asInstanceOf[List[Map[String, Long]]]
	}

}