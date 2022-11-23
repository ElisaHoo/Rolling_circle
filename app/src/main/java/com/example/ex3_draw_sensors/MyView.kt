package com.example.ex3_draw_sensors

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View

class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    // ympyrän aloituskoordinaatit sekä värin ja x- ja y-suuntaisen liikkeen alustus
    var x1 = 50f
    var y1 = 50f
    var paint = Paint()
    var xLiike = 0.toFloat()
    var yLiike = 0.toFloat()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.MAGENTA  // valitaan ympyrän väri
        canvas?.drawCircle(x1, y1, 50f, paint)  // piirretään ympyrä paikkakoordinaattien
                                                      // mukaisesti valitulla säteellä ja värillä
    }

    fun setXY(x: Float, y: Float) {
        xLiike += -x  // määritellään ympyrän liike kiihtyvyysanturista saadun tiedon mukaan (x ja y)
        yLiike += y
        x1 += xLiike  // määritellään ympyrän paikka liikkeen mukaan
        y1 += yLiike
        val xBorder = measuredWidth  // Määritellään rajat, jonka sisällä ympyrä voi liikkua (näytön koko)
        val yBorder = measuredHeight

        // Jos ylittää näytön leveyden miinus pallon koko -> liike pysähtyy ja pallo jää siihen
        if (x1 > (xBorder-100f)) {
            x1 = (xBorder-100f)
            xLiike = 0f
        }
        // ...ja sama juttu, jos alittaa näytön leveyden
        else if (x1 < (100)) {
            x1 = 100f
            xLiike = 0f
        }

        // Korkeuden osalta piti antaa enemmän pikseleitä, jotta saatiin pallo näkymään
        // (ainakin omassa emulaattorissa)
        if (y1 > (yBorder -500f)) {
            y1 = (yBorder - 500f)
            yLiike = 0f
        }

        if (y1 < (100)) {
            y1 = 100f
            yLiike = 0f
        }

        invalidate()  // pakotetaan onDrawn kutsuminen
    }
}