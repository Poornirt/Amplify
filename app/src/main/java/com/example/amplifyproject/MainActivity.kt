package com.example.amplifyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.Priority
import com.amplifyframework.datastore.generated.model.Todo
import com.amplifyframework.datastore.generated.model.Todos

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(this)

            Log.i("Tutorial", "Initialized Amplify")
        } catch (e: AmplifyException) {
            Log.e("Tutorial", "Could not initialize Amplify", e)
        }
        val item = Todos.builder()
            .name("Finish quarterly taxes")
            .priority(Priority.HIGH)
            .description("Taxes are due for the quarter next week")
            .build()
//
        Amplify.DataStore.save(
            item,
            { success -> Log.i("Tutorial", "Saved item: " + success.item().name) },
            { error -> Log.e("Tutorial", "Could not save item to DataStore", error) }
        )
//
//
//        Amplify.DataStore.query(
//            Todos::class.java,
//            Where.matches(
//                Todo.PRIORITY.eq(Priority.HIGH)
//            ),
//            { todos ->
//                while (todos.hasNext()) {
//                    val todo = todos.next()
//                    val name = todo.name;
//                    val priority: Priority? = todo.priority
//                    val description: String? = todo.description
//
//                    Log.i("Tutorial", "==== Todo ====")
//                    Log.i("Tutorial", "Name: $name")
//
//                    if (priority != null) {
//                        Log.i("Tutorial", "Priority: $priority")
//                    }
//
//                    if (description != null) {
//                        Log.i("Tutorial", "Description: $description")
//                    }
//                }
//            },
//            { failure -> Log.e("Tutorial", "Could not query DataStore", failure) }
//        )

        Amplify.DataStore.observe(Todos::class.java,
            { Log.i("Tutorial", "Observation began.") },
            { Log.i("Tutorial", it.item().toString()) },
            { Log.e("Tutorial", "Observation failed.", it) },
            { Log.i("Tutorial", "Observation complete.") }
        )
    }
}
