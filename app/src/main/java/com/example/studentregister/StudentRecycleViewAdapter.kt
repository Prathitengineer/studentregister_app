package com.example.studentregister

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregister.db.Student

class StudentRecycleViewAdapter(private val clickListener:(Student)->Unit):RecyclerView.Adapter<StudentViewHolder>() {

    var studentList = ArrayList<Student>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutflater = LayoutInflater.from(parent.context)
        val listItem = layoutflater.inflate(R.layout.list_item,parent,false)
        return StudentViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }
    fun setList(students:List<Student>){
        studentList.clear()
        studentList.addAll(students)
    }

}
class StudentViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(student: Student,clickListener:(Student)->Unit){
        val nameTextView = view.findViewById<TextView>(R.id.tvName)
        val emailTextView = view.findViewById<TextView>(R.id.etEmail)
        nameTextView.text = student.name
        emailTextView.text = student.email
        view.setOnClickListener{
            clickListener(student)
        }
    }
}