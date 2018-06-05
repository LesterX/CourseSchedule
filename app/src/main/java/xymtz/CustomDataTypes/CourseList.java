package xymtz.CustomDataTypes;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xymtz.courseschedule.R;

import java.util.ArrayList;
import java.util.List;

public class CourseList {
    private static CourseList instance;

    private List<Course> courses;

    public CourseList(){courses = new ArrayList<Course>();}

    public static CourseList get_instance(){
        if (instance == null)
            instance = new CourseList();

        return instance;
    }

    public void add_course(Course c){
        courses.add(c);
    }

    public void remove_course(Course c){
        courses.remove(c);
    }

    public void print_all(){
        for (Course c : courses){
            System.out.println(c.toString() + "\n-----\n");
        }
    }

    public List<LinearLayout> create_textviews(Context context) {
        List<LinearLayout> layout_list = new ArrayList<LinearLayout>();

        for (Course c : courses) {
            LinearLayout layout = new LinearLayout(context);

            layout.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(10, 10, 10, 10);

            TextView text = new TextView(context);
            text.setText(c.toString());
            text.setTextColor(Color.BLACK);
            text.setTextSize(18);
            text.setGravity(Gravity.CENTER);
            text.setBackground(ContextCompat.getDrawable(context,R.drawable.text_back));

            layout.addView(text);

            layout_list.add(layout);
        }

        return layout_list;
    }
}
