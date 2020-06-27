package project3.ginp14;

import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import project3.ginp14.dao.DishDao;
import project3.ginp14.dao.OrderDao;
import project3.ginp14.entity.Order;
import project3.ginp14.entity.Table;

import java.util.*;

public class test {

    public static void main(String[] args) {
        List<Table> listTable = new ArrayList<>();

        Table table1 = new Table();
        table1.setCode("1");
        table1.setId(1);

        Table table2 = new Table();
        table2.setCode("2");
        table2.setId(2);

        Table table3 = new Table();
        table3.setCode("3");
        table3.setId(3);

        Table table4 = new Table();
        table4.setCode("4");
        table4.setId(3);

        listTable.add(table4);
        listTable.add(table2);
        listTable.add(table3);
        listTable.add(table1);

        listTable.get(2).setCode("100229");

        for (Table table : listTable){
            System.out.println(table.getCode());
            System.out.println(table.getId());
            System.out.println("============");
        }
    }
}
