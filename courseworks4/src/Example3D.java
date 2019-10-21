
import java.awt.BorderLayout;
import java.awt.Container;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.PositionInterpolator;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Example3D extends JFrame{
	
	public BranchGroup createSceneGraph(){
		
		
		//creates the bound of universe
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		//creates branch group --object
		BranchGroup objectRoot = new BranchGroup();
		//creates main transform group
		TransformGroup mainTG = new TransformGroup();
		mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		//creates transform group train-body
		TransformGroup trainbody = new TransformGroup();
		trainbody.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		trainbody.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		//create transform group track
		TransformGroup track = new TransformGroup();
		track.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    track.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	    
	    //create Position interpolator for train
	    TransformGroup moves = new TransformGroup();
		moves.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		moves.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D trainmove = new Transform3D();
		Alpha m = new Alpha(-1,50000);
		PositionInterpolator rotator = new PositionInterpolator(m,moves, trainmove, 0.0f, 20.0f);
		rotator.setSchedulingBounds(bounds);
		
	    //object add main transform group
	    objectRoot.addChild(mainTG);
	    //main transform group add train-body and track
	    mainTG.addChild(track);
	    mainTG.addChild(moves);
	    moves.addChild(trainbody);
	    trainbody.addChild(rotator);
	    
	    
	    																																																																																																																																																																																																												Transform3D tem = new Transform3D();
	    tem.setTranslation(new Vector3d(0.9,0.05,-0.64));
	    TransformGroup collision = new TransformGroup(tem);
	   
		//create a transform group for track
		Transform3D t1 = new Transform3D();
		t1.rotZ(Math.PI/2*3);
		t1.setTranslation(new Vector3d(-0.4,-0.1,-0.73));
		TransformGroup line1 = new TransformGroup(t1);
		
		//create a transform group for track
		Transform3D t2 = new Transform3D();
		t2.rotZ(Math.PI/2*3);
		t2.setTranslation(new Vector3d(-0.4,-0.1,-0.56));
		TransformGroup line2 = new TransformGroup(t2);
	    
	    //create transform group box that is part of train-body
	    Transform3D bb = new Transform3D();
	    bb.rotX(Math.PI/2);
	    bb.setTranslation(new Vector3d(-0.6,0.0,-0.64));
		TransformGroup boxbody = new TransformGroup(bb);
	    
		//create transform group box that is part of train-body
	    Transform3D bback = new Transform3D();
	    bback.setTranslation(new Vector3d(-0.84,-0.041,-0.64));
		TransformGroup boxbodyback = new TransformGroup(bback);
	    
		//create transform group box that is part of train-body
	    Transform3D btop = new Transform3D();
	    btop.setTranslation(new Vector3d(-0.55,0.07,-0.64));
		TransformGroup boxbodytop = new TransformGroup(btop);
		
		//create transform group box that is part of train-body
	    Transform3D bc = new Transform3D();
	    bc.setTranslation(new Vector3d(-0.68,0.11,-0.64));
		TransformGroup bodyc = new TransformGroup(bc);
		
		//create transform group cylinder that is part of train-body
	    Transform3D bcc = new Transform3D();
	    bcc.setTranslation(new Vector3d(-0.8,0.068,-0.64));
		TransformGroup bodycc = new TransformGroup(bcc);
		
		//create transform group cone that is part of train-body
	    Transform3D bcone = new Transform3D();
	    bcone.setTranslation(new Vector3d(-0.68,0.2,-0.64));
		TransformGroup bodycone = new TransformGroup(bcone);
		
		//create a transform group for wheel
		Transform3D w1 = new Transform3D();
		w1.rotX(Math.PI/2);
		w1.setTranslation(new Vector3d(-0.87,-0.09,-0.56));
		TransformGroup twheel1 = new TransformGroup(w1);
		
		//create a transform group for wheel
		Transform3D w2 = new Transform3D();
		w2.rotX(Math.PI/2);
		w2.setTranslation(new Vector3d(-0.87,-0.09,-0.73));
		TransformGroup twheel2 = new TransformGroup(w2);
		
		//create a transform group for wheel
		Transform3D w3 = new Transform3D();
		w3.rotX(Math.PI/2);
		w3.setTranslation(new Vector3d(-0.5,-0.09,-0.56));
		TransformGroup twheel3 = new TransformGroup(w3);
		
		//create a transform group for wheel
		Transform3D w4 = new Transform3D();
		w4.rotX(Math.PI/2);
		w4.setTranslation(new Vector3d(-0.5,-0.09,-0.73));
		TransformGroup twheel4 = new TransformGroup(w4);
		
	    //create transform group lightning
		Transform3D light = new Transform3D();
		light.setTranslation(new Vector3d(-0.8,0.2,-0.64));
		TransformGroup lightning= new TransformGroup(light);
		
		//set rotation for star
		TransformGroup starss = new TransformGroup();
		starss.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		starss.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D st = new Transform3D();
		Alpha ss = new Alpha(-1,1000);
		RotationInterpolator starsss = new RotationInterpolator(ss,starss, st, 0.0f, (float) Math.PI * (2.0f));
		starsss.setSchedulingBounds(bounds);
		//create transform group create shape star
		Transform3D s = new Transform3D();
		s.setTranslation(new Vector3d(0.2,0.2,-0.64));
		TransformGroup star= new TransformGroup(s);
		Shape3D stars= new star();
		mainTG.addChild(star);
		star.addChild(starss);
		starss.addChild(stars);
		starss.addChild(starsss);
		
		//create rotation for pentagonss
		TransformGroup pentagonss = new TransformGroup();
		pentagonss.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		pentagonss.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D pe = new Transform3D();
		Alpha pp = new Alpha(-1,2000);
		RotationInterpolator pentagonsss = new RotationInterpolator(pp,pentagonss, pe, 0.0f, (float) Math.PI * (-2.0f));
		pentagonsss.setSchedulingBounds(bounds);
		//create transform group create shape pentagon
		Transform3D p = new Transform3D();
		p.setTranslation(new Vector3d(0.2,0.2,-0.64));
		TransformGroup pentagon= new TransformGroup(p);
		mainTG.addChild(pentagon);
		pentagon.addChild(pentagonss);
		Shape3D pentagons= new pentagon();
		pentagonss.addChild(pentagons);
		pentagonss.addChild(pentagonsss);
		
		
		//create rotation for window
		TransformGroup triangles = new TransformGroup();
		triangles.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		triangles.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D tr = new Transform3D();
		Alpha tt = new Alpha(-1,1000);
		RotationInterpolator trianglesss = new RotationInterpolator(tt,triangles, tr, 0.0f, (float) Math.PI * (2.0f));
		trianglesss.setSchedulingBounds(bounds);
		//create transform group create shape pentagon
		Transform3D t = new Transform3D();
		t.setTranslation(new Vector3d(-0.80,0.068,-0.56));
		TransformGroup triangle= new TransformGroup(t);
		trainbody.addChild(triangle);
		triangle.addChild(triangles);
		Shape3D trianglessss= new traingle();
		triangle.addChild(trianglessss);
		//triangles.addChild(trianglesss);
		
		
		
		
		//creating a rotation interpolator for wheels
		TransformGroup wheels1 = new TransformGroup();
		wheels1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		wheels1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D ws1 = new Transform3D();
		Alpha rw1 = new Alpha(-1,1000);
		RotationInterpolator rotatorw1 = new RotationInterpolator(rw1,wheels1, ws1, 0.0f, (float) Math.PI * (2.0f));
		rotatorw1.setSchedulingBounds(bounds);
		
		TransformGroup wheels2 = new TransformGroup();
		wheels2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		wheels2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D ws2 = new Transform3D();
		Alpha rw2 = new Alpha(-1,1000);
		RotationInterpolator rotatorw2 = new RotationInterpolator(rw2,wheels2, ws2, 0.0f, (float) Math.PI * (2.0f));
		rotatorw2.setSchedulingBounds(bounds);
		
		TransformGroup wheels3 = new TransformGroup();
		wheels3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		wheels3.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D ws3 = new Transform3D();
		Alpha rw3 = new Alpha(-1,1000);
		RotationInterpolator rotatorw3 = new RotationInterpolator(rw3,wheels3, ws3, 0.0f, (float) Math.PI * (2.0f));
		rotatorw3.setSchedulingBounds(bounds);
		
		TransformGroup wheels4 = new TransformGroup();
		wheels4.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		wheels4.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D ws4 = new Transform3D();
		Alpha rw4 = new Alpha(-1,1000);
		RotationInterpolator rotatorw4 = new RotationInterpolator(rw4,wheels4, ws4, 0.0f, (float) Math.PI * (2.0f));
		rotatorw4.setSchedulingBounds(bounds);
		
		//creating a rotation interpolator for ball
		TransformGroup ligh = new TransformGroup();
		ligh.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		ligh.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D bal = new Transform3D();
		Alpha bccc = new Alpha(-1,1000);
		RotationInterpolator rotato = new RotationInterpolator(bccc,ligh, bal, 0.0f, (float) Math.PI * (2.0f));
		rotato.setSchedulingBounds(bounds);
		
		//create an appearance for main body of train
	    Appearance body = new Appearance();
		Color3f color= new Color3f(0.8f, 0.8f, 0.8f);
		ColoringAttributes cbody = new ColoringAttributes();
		cbody.setColor(color);
		body.setColoringAttributes(cbody);
		//create an appearance for wheel
	    Appearance wh = new Appearance();
		Color3f colorwh= new Color3f(1.0f, 0.0f, 0.0f);
		ColoringAttributes cw = new ColoringAttributes();
		cw.setColor(colorwh);
		wh.setColoringAttributes(cw);	
		//create an appearance for wheel
	    Appearance tracks = new Appearance();
		Color3f colortracks= new Color3f(1.0f, 1.0f, 1.0f);
		ColoringAttributes ct = new ColoringAttributes();
		ct.setColor(colortracks);
		tracks.setColoringAttributes(ct);
		
		//create an appearance for chimney
	    Appearance chimney = new Appearance();
		Color3f colorchimney= new Color3f(0.741f, 0.718f, 0.420f);
		ColoringAttributes cc = new ColoringAttributes();
		cc.setColor(colorchimney);
		chimney.setColoringAttributes(cc);
		
		//create appearances for collision
	    Appearance bcolor1 = new Appearance();
		Color3f colorb1= new Color3f(1.0f, 0.0f, 0.0f);
		ColoringAttributes ca1 = new ColoringAttributes();
		ca1.setColor(colorb1);
		bcolor1.setColoringAttributes(ca1);
	   
		Appearance bcolor2 = new Appearance();
		Color3f colorb2= new Color3f(0.0f, 0.0f, 1.0f);
		ColoringAttributes ca2 = new ColoringAttributes();
		ca2.setColor(colorb2);
		bcolor2.setColoringAttributes(ca2);	
		
	    Appearance bcolor3 = new Appearance();
		Color3f colorb3= new Color3f(0.0f, 1.0f, 0.0f);
		ColoringAttributes ca3 = new ColoringAttributes();
		ca3.setColor(colorb3);
		bcolor3.setColoringAttributes(ca3);
		
		//create light 
		Color3f col= new Color3f(0.373f, 0.620f, 0.627f);
		Vector3f co = new Vector3f(-0.68f,-0.1f,-0.64f);
		DirectionalLight l1 = new DirectionalLight(col, co);
		l1.setInfluencingBounds(bounds);
		Color3f colc= new Color3f(0.741f, 0.718f, 0.420f);
		Vector3f coc = new Vector3f(-0.68f,-0.1f,-0.64f);
		DirectionalLight l3 = new DirectionalLight(colc, coc);
		l3.setInfluencingBounds(bounds);
		Color3f white= new Color3f(0.902f, 0.902f, 0.980f);
		Vector3f whi = new Vector3f(0.68f,0.1f,0.64f);
		DirectionalLight l2 = new DirectionalLight(white, whi);
		l2.setInfluencingBounds(bounds);
		Color3f colcc= new Color3f(0.804f, 0.361f, 0.361f);
		Vector3f cocc = new Vector3f(-0.68f,-0.1f,-0.64f);
		DirectionalLight l4 = new DirectionalLight(colcc, cocc);
		l4.setInfluencingBounds(bounds);
		
		Appearance middle = new Appearance();
		Color3f colorc= new Color3f(1.000f, 0.894f, 0.769f);
		ColoringAttributes ccc = new ColoringAttributes();
		ccc.setColor(colorc);
		middle.setColoringAttributes(ccc);
		
		
		Appearance top = new Appearance();
		Color3f colorcc= new Color3f(0.804f, 0.522f, 0.247f);
		ColoringAttributes cccc = new ColoringAttributes();
		cccc.setColor(colorcc);
		top.setColoringAttributes(cccc);
		
		//create the main body of train
		Box box = new Box(0.15f,0.08f,0.05f,middle);
		//create the main body of train
		Box boxback = new Box(0.1f,0.008f,0.08f,body);
		//create the main body of train
		Box boxtop = new Box(0.1f,0.02f,0.08f,top);
		//create the main body of train
		Box cylinder = new Box(0.04f,0.07f,0.03f,chimney);
		//create the main body of train
		Cone cone = new Cone(0.04f,0.05f,body);
		//create line1
		Cylinder li1= new Cylinder(0.01f,50.0f,tracks); 
		//create line2
		Cylinder li2= new Cylinder(0.01f,50.0f,tracks);
		//create cap
		Cylinder cap= new Cylinder(0.08f,0.2f,tracks); 
		trainbody.addChild(bodycc);
		bodycc.addChild(cap);
		//create collision sphere
		Sphere b1 = new Sphere(0.1f);b1.setAppearance(bcolor1);
		Sphere b2 = new Sphere(0.1f);b2.setAppearance(bcolor2);
		Sphere b3 = new Sphere(0.1f);b3.setAppearance(bcolor3);
		
		
		//create switch
		Switch switc = new Switch();
		switc.addChild(b1);
		switc.addChild(b2);
		switc.addChild(b3);
		switc.setWhichChild(0);
		switc.setCapability(Switch.ALLOW_SWITCH_WRITE);
		switc.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		switc.setCollisionBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),0.0f)); 
		switc.setCollidable(true);
		CollisionBehaviour1 scb = new CollisionBehaviour1(b1,switc,bounds);
		
		//create wheels
		Cylinder wheel1 = new Cylinder(0.05f,0.05f);
		Cylinder wheel2 = new Cylinder(0.05f,0.05f);
		Cylinder wheel3 = new Cylinder(0.05f,0.05f);
		Cylinder wheel4 = new Cylinder(0.05f,0.05f);
		
		//create sphere lightning
		Sphere lig = new Sphere(0.05f);
		
		//main transform group add collision
		mainTG.addChild(collision);
		mainTG.addChild(scb);
		collision.addChild(switc);		
		//track transform group add transform group line1
		track.addChild(line1);
		//track transform group add transform group line1
		track.addChild(line2);
		//train-body transform group add transform group body-box
		trainbody.addChild(boxbody);
		//train-body transform group add transform group back-body-box
		trainbody.addChild(boxbodyback);
		//train-body transform group add transform group top-body-box
		trainbody.addChild(boxbodytop);
		//train-body transform group add transform group body-cylinder
		trainbody.addChild(bodyc);
		//train-body transform group add transform group wheel
	    trainbody.addChild(twheel1);
		//train-body transform group add transform group wheel
		trainbody.addChild(twheel2);
		//train-body transform group add transform group wheel
		trainbody.addChild(twheel3);
		//train-body transform group add transform group wheel
		trainbody.addChild(twheel4);
		//train-body transform group add transform group body-cone
		trainbody.addChild(bodycone);
		//transform group body-cone add shape cone
		bodycone.addChild(cone);
		//train-body transform group add transform group lightning
		trainbody.addChild(lightning);
		//lightning transform group add sphere lig
		lightning.addChild(l1);
		lightning.addChild(l2);
		lightning.addChild(l3);
		lightning.addChild(l4);
		lightning.addChild(ligh);
		ligh.addChild(rotato);
		ligh.addChild(lig);
		//body-box transform group add cylinder
		bodyc.addChild(cylinder);
		//body-box transform group add box
		boxbody.addChild(box);
		//back-body-box transform group add box
		boxbodyback.addChild(boxback);
		//top-body-box transform group add box
		boxbodytop.addChild(boxtop);
		//wheel transform group add wheels interpolator tramsform group
		twheel1.addChild(wheels1);
		//wheel transform group add wheels interpolator tramsform group
		twheel2.addChild(wheels2);
		//wheel transform group add wheels interpolator tramsform group
		twheel3.addChild(wheels3);
		//wheel transform group add wheels interpolator tramsform group
		twheel4.addChild(wheels4);
		//wheels1 add wheel and rotator
		wheels1.addChild(wheel1);
		wheels1.addChild(rotatorw1);
		//wheels2 add wheel and rotator
		wheels2.addChild(wheel2);
		wheels2.addChild(rotatorw2);
		//wheels3 add wheel and rotator
		wheels3.addChild(wheel3);
		wheels3.addChild(rotatorw3);
		//wheels4 add wheel and rotator
		wheels4.addChild(wheel4);
		wheels4.addChild(rotatorw4);
		//line1 transform group add li1
		line1.addChild(li1);
		//line2 transform group add li2
		line2.addChild(li2);
		
		
	    
		// Create the rotate behavior node
		MouseRotate behavior = new MouseRotate();
		behavior.setTransformGroup(mainTG);
		objectRoot.addChild(behavior);
		behavior.setSchedulingBounds(bounds);
		
		// Create the zoom behavior node
		MouseZoom behavior2 = new MouseZoom();
		behavior2.setTransformGroup(mainTG);
		objectRoot.addChild(behavior2);	
	        behavior2.setSchedulingBounds(bounds);
		
		// Create the translate behavior node
		 MouseTranslate behavior3 = new MouseTranslate();
		behavior3.setTransformGroup(mainTG);
		objectRoot.addChild(behavior3);
		behavior3.setSchedulingBounds(bounds);
		
		
		
		//object compile
		objectRoot.compile();
		//return object
		return objectRoot;
	}
	
	public Example3D (){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Container cp = getContentPane();
	cp.setLayout(new BorderLayout());
	Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration() );
	cp.add("Center", c);
	BranchGroup scene = createSceneGraph();
	SimpleUniverse u = new SimpleUniverse(c);
	u.getViewingPlatform().setNominalViewingTransform();
	u.addBranchGraph(scene);
        setTitle("Scene Graph");
        setSize(1024,700);
        setVisible(true);

	}
	
	public static void main(String[] args){
		Example3D play = new Example3D();
		
	}

}
