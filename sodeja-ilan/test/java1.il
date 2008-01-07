import javax.swing.JFrame
import javax.swing.JButton

import java.beans.PropertyChangeListener
import java.awt.event.ActionListener

f = JFrame new
f addPropertyChangeListener (promoteTo PropertyChangeListener \ pce = print ("Prop: " concat (pce getPropertyName)))
f setTitle "Alabala 12"

b = JButton new
b setText "Test"
b addActionListener (promoteTo ActionListener \ ae = exit 0)

f add b
f setBounds 100 100 200 200
if (f isVisible) {
	print "11"
} else {
	print "12"
}

f setVisible true
if (f isVisible) {
	print "21"
} else {
	print "22"
}

fun t k = {
	print (k toString)
	t (k + 1)
}

t 0
