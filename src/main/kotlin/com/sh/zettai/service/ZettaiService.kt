package com.sh.zettai.service

import com.sh.zettai.domain.ListName
import com.sh.zettai.domain.ToDoItem
import com.sh.zettai.domain.ToDoList
import com.sh.zettai.domain.User
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ZettaiService {
    fun getToDoList(usersList: Pair<User, ListName>): ToDoList =
        usersList.let(::fetchListContent)

    private fun fetchListContent(listId: Pair<User, ListName>): ToDoList {
        val lists = getDummyMap(listId.first, listId.second)

        return lists[listId.first]
            ?.firstOrNull { it.listName == listId.second }
            ?: error("List unknown")
    }

    private fun getDummyMap(user: User, listName: ListName): Map<User, List<ToDoList>> {
        val items = listOf("carrots", "apples", "milk")

        val toDoList = ToDoList(
            ListName(listName.name),
            items.map(::ToDoItem)
        )
        return mapOf(User(user.name) to listOf(toDoList))
    }
}
