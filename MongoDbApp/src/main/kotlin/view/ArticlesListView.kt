package view

import javafx.collections.FXCollections
import javafx.scene.control.SelectionMode
import tornadofx.View
import tornadofx.listview
import tornadofx.onUserSelect
import tornadofx.tableview
import java.time.LocalDate

//class ArticlesListView : View() {
//    private val persons = FXCollections.observableArrayList(
//        Person(1, "Samantha Stuart", LocalDate.of(1981,12,4)),
//        Person(2, "Tom Marks", LocalDate.of(2001,1,23)),
//        Person(3, "Stuart Gills", LocalDate.of(1989,5,23)),
//        Person(3, "Nicole Williams", LocalDate.of(1998,8,11))
//    )
//
//    override val root = tableview( persons) {
//        column("ID", Person::id)
//        column("Name", Person::name)
//        column("Birthday", Person::birthday)
//        column("Age", Person::age)
//        columnResizePolicy = SmartResize.POLICY
//    }
//}

class ArticlesListView(articleView: ArticleView) : View() {
    override val root = listview<String> {
        items.add("Alpha")
        items.add("Beta")
        items.add("Gamma")
        items.add("Delta")
        items.add("Epsilon")
        selectionModel.selectionMode = SelectionMode.SINGLE
        onUserSelect {

        }
    }
}