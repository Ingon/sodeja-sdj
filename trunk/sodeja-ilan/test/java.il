import javax.swing.JFrame

fun printValue pce = {
	value = (pce getNewValue) toString
	print ("Value: " concat value)
}

printName = \ pce = {
	name = pce getPropertyName
	print ("Property: " concat name)
}

frame = JFrame new
frame addPropertyChangeListener printName
frame addPropertyChangeListener printValue
frame setTitle "Alabala 1"
frame setBounds 100 100 200 200
frame setDefaultCloseOperation 3
frame setVisible true

