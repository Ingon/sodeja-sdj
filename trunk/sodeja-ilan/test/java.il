import javax.swing.JFrame

frame = JFrame new
frame addPropertyChangeListener (\ pce = print (pce getPropertyName))
frame setTitle "Alabala 1"
frame setBounds 100 100 200 200
frame setVisible true
frame setDefaultCloseOperation 3 ; TODO Missing constrants
