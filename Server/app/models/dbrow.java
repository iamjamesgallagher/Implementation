package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

import com.avaje.ebean.*;


/**
 * Company entity managed by Ebean
 */
@Entity 
public class dbrow extends Model {

    private static final long serialVersionUID = 1L;

	@Id
    public Long id;
    
    @Constraints.Required
    public String name;

    
    @Constraints.Required
    public String sector;    
    
    /**
     * Generic query helper for entity STOCK with id Long
     */
    public static Model.Finder<Long,dbrow> find = new Model.Finder<Long,dbrow>(Long.class, dbrow.class);


  public static dbrow findById(long id) {
        return find.byId(id);
    }

    public static List<dbrow> findAll() {
        return find.all();
    }


    /*public static Page<Stock> find() {
        return 
            find.where()
                .ilike("name", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .fetch("company")
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }
*/

    /*public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(stock c: stock.find.orderBy("name").findList()) {
            options.put(c.id.toString(), c.name);
        }
        return options;
    }*/

}

