; ----------------------------------------
; Name : Tweening functions
; Date : (C)2025 
; Site : https://github.com/BlackCreepyCat
; ----------------------------------------

Graphics3D 800, 600, 0, 2
Local camera = CreateCamera()
PositionEntity camera, 0, 0, -8

light = CreateLight(2)
PositionEntity light, 8, 8, -8
LightRange light, 11

Local t# = 0.0
Local speed# = 0.02

Local posX#, posY#, posZ#

; Valeurs pour l'interpolation
Global startValue# = 0
Global  endValue# = 2

Vector_A.Vector3 = CreateVector3(-5,0,0)
Vector_B.Vector3  = CreateVector3(0,0,0)
Vector_C.Vector3  = CreateVector3(5,0,0)

While Not KeyHit(1) ; Touche ESC pour quitter
	
	; Appliquer l'interpolation
	Vector_A\Y# = SineIn#(t#)
	Vector_B\Y# = SineOut#(t#)
	Vector_C\Y# = CubicInOut#(t#)
	
	; Appliquer la position du cube
	PositionEntity Vector_A\Entity% , Vector_A\X# , Vector_A\Y# , Vector_A\Z#
	PositionEntity Vector_B\Entity% , Vector_B\X# , Vector_B\Y# , Vector_B\Z#
	PositionEntity Vector_C\Entity% , Vector_C\X# , Vector_C\Y# , Vector_C\Z#
	
	; Changer la couleur du cube en fonction de t#
	RedrawCubeColor(Vector_A\Entity%, t#)
	RedrawCubeColor(Vector_B\Entity%, t#)
	RedrawCubeColor(Vector_C\Entity%, t#)
	
	; Rendu de la scène
	RenderWorld
	
	; Affichage debug
	Text 10, 10, "t: " + t#
	
	; Mise à jour de t
	t# = t# + speed#
	
	If t# >= endValue# Or t# <= startValue# Then
		speed# = -speed#  ; Inverser la direction
		
		If t# > endValue# Then t# = endValue# ; Limiter t# à 1 si > 1
		If t# <  startValue# Then t# =  startValue#  ; Limiter t# à 0 si < 0
	End If
	
	Flip
Wend

End

Type Vector3
	
	Field X#
    Field Y#
    Field Z#
	
	Field Entity%
	
End Type

; Constructeur
Function CreateVector3.Vector3(x#, y#, z#, Debug = True)
    Local N.Vector3 = New Vector3
    N\X# = x#
    N\Y# = y#
    N\Z# = z#
	
    If Debug Then
        N\Entity% = CreateCube()
        PositionEntity N\Entity%, N\X#, N\Y#, N\Z#
    Else
        N\Entity% = 0
    End If
	
    Return N
End Function

; Fonction pour modifier la couleur du cube
Function RedrawCubeColor(entity, t#)
	;Local colorFactor# = (t# + 1) / 2 ; Transforme la valeur de t# en un facteur de couleur entre 0 et 1
	Local colorFactor# = t# / endValue#
	; Limiter la valeur pour éviter des erreurs de couleur
	If colorFactor# > 1 Then colorFactor# = 1
	If colorFactor# < 0 Then colorFactor# = 0
	
	; Utilise une interpolation pour changer la couleur
	Local red# = colorFactor# * 255
	Local green# = (1 - colorFactor#) * 255
	EntityColor entity, red#, green#, 0
End Function

; Fonction linéaire
Function Linear#(x#)
	Return x#
End Function

; Fonction SineIn
Function SineIn#(x#, amp# = 50 )
	Return 1 - Cos(x# * Pi * amp# )
End Function

; Fonction SineOut
Function SineOut#(x#, amp# = 50 )
	Return Sin(x# * Pi * amp# )
End Function

; Fonction CubicIn
Function CubicIn#(x#)
	Return x# * x# * x#
End Function

; Fonction CubicOut
Function CubicOut#(x#)
	Return 1 - (1 - x#) ^ 3
End Function

; Fonction CubicInOut
Function CubicInOut#(x# , amp# = 2.1 )
	If x# < 0.5
		Return 4 * x# * x# * x#
	Else
		Return 1 - (-2 * x# + 2) ^ 3 / amp#
	End If
End Function

; Fonction QuadIn
Function QuadIn#(x#)
	Return x# * x#
End Function

; Fonction QuadOut
Function QuadOut#(x#)
	Return 1 - (1 - x#) ^ 2
End Function

; Fonction QuadInOut
Function QuadInOut#(x#)
	If x# < 0.5
		Return 2 * x# * x#
	Else
		Return 1 - (-2 * x# + 2) ^ 2 / 2
	End If
End Function

; Fonction QuartIn
Function QuartIn#(x#)
	Return x# * x# * x# * x#
End Function

; Fonction QuartOut
Function QuartOut#(x#)
	Return 1 - (1 - x#) ^ 4
End Function

; Fonction QuartInOut
Function QuartInOut#(x#)
	If x# < 0.5
		Return 8 * x# * x# * x# * x#
	Else
		Return 1 - (-2 * x# + 2) ^ 4 / 2
	End If
End Function

; Fonction QuintIn
Function QuintIn#(x#)
	Return x# * x# * x# * x# * x#
End Function

; Fonction QuintOut
Function QuintOut#(x#)
	Return 1 - (1 - x#) ^ 5
End Function

; Fonction QuintInOut
Function QuintInOut#(x#)
	If x# < 0.5
		Return 16 * x# * x# * x# * x# * x#
	Else
		Return 1 - (-2 * x# + 2) ^ 5 / 2
	End If
End Function

;~IDEal Editor Parameters:
;~F#5D#6B
;~C#Blitz3D