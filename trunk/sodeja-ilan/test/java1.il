import javax.swing.JFrame
import java.beans.PropertyChangeListener

class Frame {
	fun Frame = {
		@frame = JFrame new
	} 
	
	fun title str = @frame setTitle str
	
	fun addPropertyChangeListener pcl = {
		@frame addPropertyChangeListener (promoteTo PropertyChangeListener pcl)
	}
	
	fun show = @frame setVisible true
}

f = Frame new
f addPropertyChangeListener \ pce = print ("Prop: " concat (pce getPropertyName))
f title "Alabala 12"
f show
