

import javax.media.j3d.Appearance;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Shape3D;

public class pentagon extends Shape3D{
    public pentagon( ) {
    	float vert1[]=
    		{-0.05f,0.15f,0.0f, -0.07f,0.3f,0.0f,
    			-0.05f,0.15f,0.0f, 0.05f,0.15f,0.0f,  
    			0.05f,0.15f,0.0f, 0.07f,0.3f,0.0f,
    			0.07f,0.3f,0.0f, 0.0f,0.38f,0.0f,
    			0.0f,0.38f,0.0f,-0.07f,0.3f,0.0f,
    		 };
    	 float color1[ ]={1.000f, 0.000f, 0.000f,  1.000f, 0.647f, 0.000f, 1.000f, 1.000f, 0.000f,  0.000f, 0.502f, 0.000f, 0.000f, 0.000f, 1.000f,  0.737f, 0.561f, 0.561f, 0.502f, 0.000f, 0.502f,  1.000f, 0.980f, 0.980f,
    	    		.0f,0.5f,1.0f,  1.0f,1.5f,1.0f,};
        LineArray line=new LineArray(10,LineArray.COORDINATES|LineArray.COLOR_3);
        line.setColors(0,color1);
        line.setCoordinates(0,vert1);
        LineAttributes linea=new LineAttributes( );
        linea.setLineAntialiasingEnable(true);
        linea.setLineWidth(1.5f);
        Appearance app=new Appearance( );
        app.setLineAttributes(linea);
        this.setGeometry(line);
        this.setAppearance(app);
    
    }
}
