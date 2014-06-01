package thebounzer.org.oglClock;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.*;
import java.time.LocalTime;

/**
 *
 * @author thbounzer
 */
public class OglClock implements GLEventListener {
    
    
    public static void main(String[] args) {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        caps.setSampleBuffers(true);
        caps.setNumSamples(3);
        GLCanvas canvas = new GLCanvas(caps);
        Frame frame = new Frame("AWT Window Test");
        frame.setSize(600, 600);
        frame.add(canvas);
        frame.setVisible(true);
        int fps = 5;
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        canvas.addGLEventListener(new OglClock());
        FPSAnimator animator = new FPSAnimator(canvas,fps);  
        animator.start();
        
    }    

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST);
        gl.glShadeModel(GL2.GL_SMOOTH);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        render(drawable);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }
   

    private void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glBegin(GL.GL_TRIANGLES);
        LocalTime timeNow = LocalTime.now();
        int localHour = timeNow.getHour();
        double localSec  = timeNow.getSecond();
        int localMin = timeNow.getMinute();
        double currentSecMainAngle = ((Math.PI*2)/360)*((localSec*-6)+90);
        double currentMinMainAngle = ((Math.PI*2)/360)*((localMin*-6)+90);
        double currentHourMainAngle = ((Math.PI*2)/360)*((localHour*30)+90);
        rotatingIndicator(gl, currentSecMainAngle,15,1);
        rotatingIndicator(gl, currentMinMainAngle,15,2);
        rotatingIndicator(gl, currentHourMainAngle,15,3);
        gl.glEnd();
        
    }
    
    private void rotatingIndicator(GL2 gl,double angle,int scalingWidth, int scalingLength){
        gl.glColor3f(1, 0, 0);
        gl.glVertex2d(Math.cos(angle)/scalingLength, Math.sin(angle)/scalingLength);
        gl.glColor3f(0, 1, 0);
        gl.glVertex2d(Math.cos(angle+90)/scalingWidth, Math.sin(angle+90)/scalingWidth);         
        gl.glColor3f(0, 1, 0);
        gl.glVertex2d(Math.cos(angle-90)/scalingWidth, Math.sin(angle-90)/scalingWidth);      
    }
    
}
