package com.example.studentregister

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregister.db.Student
import com.example.studentregister.db.StudentDatabase

class MainActivity(private var viewModel: StudentViewModel,private var adapter: StudentRecycleViewAdapter,
                   private var selectedStudent: Student ) : AppCompatActivity() {
    val studentRecycleView: RecyclerView = findViewById(R.id.rvStudent)
    var isListItemClicked = false

    val nameEditText = findViewById<EditText>(R.id.etName)
    val emailEditText = findViewById<EditText>(R.id.etEmail)
    val saveButton = findViewById<Button>(R.id.btnsave)
    val clearButton = findViewById<Button>(R.id.btnclear)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)

        val viewModelst = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        saveButton.setOnClickListener {

            if (isListItemClicked){
                viewModelst.updateStudent(
                    Student(
                        selectedStudent.id,
                        nameEditText.text.toString(),
                        emailEditText.text.toString()
                    )
                )
                saveButton.text = "Save"
                clearButton.text = "Clear"
                isListItemClicked = false
                nameEditText.setText("")
                emailEditText.setText("")
            }
            else {
                viewModelst.insertStudent(
                    Student(
                        0,
                        nameEditText.text.toString(),
                        emailEditText.text.toString()
                    )
                )
                nameEditText.setText("")
                emailEditText.setText("")
            }
        }
        clearButton.setOnClickListener {
            if (isListItemClicked){
                viewModelst.deleteStudent(
                    Student(
                        selectedStudent.id,
                        nameEditText.text.toString(),
                        emailEditText.text.toString()
                    )
                )
                saveButton.text = "Save"
                clearButton.text = "Clear"
                isListItemClicked = false
                nameEditText.setText("")
                emailEditText.setText("")
            }else {
                viewModelst.insertStudent(
                    Student(
                        0,
                        nameEditText.text.toString(),
                        emailEditText.text.toString()
                    )
                )
                nameEditText.setText("")
                emailEditText.setText("")
            }
        }
        initRecyclerView()
    }
    fun initRecyclerView(){
        studentRecycleView.adapter = adapter
        studentRecycleView.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecycleViewAdapter{
                selectedItem:Student -> listItemClicked(selectedItem)
        }

        displayStudentList()
    }
    fun displayStudentList() = viewModel.students.observe(this) {
        adapter.setList(it)
        adapter.notifyDataSetChanged()
    }

    fun listItemClicked(student: Student){
//        Toast.makeText(this, "Student name is ${student.name}", Toast.LENGTH_SHORT).show()
        selectedStudent = student
        saveButton.text = "Update"
        clearButton.text = "Delete"
        isListItemClicked = true
        nameEditText.setText(selectedStudent.name)
        emailEditText.setText(selectedStudent.email)
    }
}
//    private fun SaveStudentData(){
//        viewModel.insertStudent(
//            Student(
//                0,
//                nameEditText.text.toString(),
//                emailEditText.text.toString()
//            )
//        )
//    }

//    private fun updateStudentData(){
//        viewModel.updateStudent(
//            Student(
//                selectedStudent.id,
//                nameEditText.text.toString(),
//                emailEditText.text.toString()
//            )
//        )
//        saveButton.text = "Save"
//        clearButton.text = "Clear"
//        isListItemClicked = false
//    }

//    private fun deleteStudentData(){
//        viewModel.deleteStudent(
//            Student(
//                selectedStudent.id,
//                nameEditText.text.toString(),
//                emailEditText.text.toString()
//            )
//        )
//        saveButton.text = "Save"
//        clearButton.text = "Clear"
//        isListItemClicked = false
//    }
//    private fun clearInput(){
//        nameEditText.setText("")
//        emailEditText.setText("")
//    }
