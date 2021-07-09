//	LUIS JOSÉ IXTA ZAMUDIO 17420565
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Letter3D extends JFrame{
	Image icon;
	
	public Letter3D() {
		setTitle("Letra 3D");
		setSize(800,600);
		setLocationRelativeTo(this);
		
		//	ICONO DE LA INTERFAZ
		icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Resources/icon.png"));
		setIconImage(icon);
		
		//	PARA LA CONFIGURACIÓN GRÁFICA DEL SISTEMA
		GraphicsConfiguration config=SimpleUniverse.getPreferredConfiguration();
		
		//	CONSTRUIR EL CANVAS 3D
		Canvas3D canvas=new Canvas3D(config);
		add(canvas);
		
		//	CREAR LA RAMA DE PRESENTACIÓN
		BranchGroup scene=createScene();
		
		//	COMPILAR LA ESCENA PARA LA EJECUCIÓN
		scene.compile();
		
		//	PARA SIMPLIFICAR LA RAMA DE REPESENTACIÓN
		SimpleUniverse sU=new SimpleUniverse(canvas);
		
		//	EL PUNTO DE VISIÓN SE RETRAZA PARA PODER VER LA FIGURA
		sU.getViewingPlatform().setNominalViewingTransform();
		
		//	SE LE AÑADE LA RAMA DE ESCENA A LA RAIZ UNIVERSO SIMPLE
		sU.addBranchGraph(scene);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// CREAR FIGURA 3D
	public void setFigure(BranchGroup root) {
		
		//	PONER FONDO
		URL ruta = getClass().getResource("Resources/red_volcano.jpg");
		TextureLoader loader = new TextureLoader(ruta, this);
		ImageComponent2D image = loader.getImage();
		Background bg = new Background(image);
		BoundingSphere limite=new BoundingSphere();
		bg.setApplicationBounds(limite);
		
		//	CREAR VERTICES DE LA FIGURA 3D
		Point3d fig3D[] = new Point3d[] {				
				new Point3d(-.3,-.4,.1),	//A 0
				new Point3d(.3,-.4,.1),		//B 1
				new Point3d(.3,-.2,.1),		//C 2
				new Point3d(-.1,-.2,.1),	//D 3
				new Point3d(-.1,.4,.1),		//E 4
				new Point3d(-.3,.4,.1),		//F 5
				new Point3d(-.3,-.4,-.1),	//G 6
				new Point3d(.3,-.4,-.1),	//H 7
				new Point3d(.3,-.2,-.1),	//I 8
				new Point3d(-.1,-.2,-.1),	//J 9
				new Point3d(-.1,.4,-.1),	//K 10
				new Point3d(-.3,.4,-.1),	//L 11
		};
			
		//	SECUENCIA PARA EL TRAZO DE LINEAS DE PUNTO A PUNTO
		int sequence[] = {0,1,2,3,4,5, 6,7,8,9,10,11, 0,1,7,6, 1,2,8,7, 2,3,9,8, 3,4,10,9, 4,5,11,10, 5,0,6,11};
		
		GeometryInfo gInfo=new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
		gInfo.setCoordinates(fig3D);
		gInfo.setCoordinateIndices(sequence);
		int bands[]= {6,6,4,4,4,4,4,4};
		gInfo.setStripCounts(bands);
		
		// DEFINIR COLORES PARA RELLENAR LA FIGURA
		Color3f vc[] = new Color3f[] {
				new Color3f(.8f,.8f,.8f), 
				new Color3f(.9f,.24f,0) };
		int secC[] = {
				0,0,0,0,0,0, 
				1,1,1,1,1,1, 
				0,0,1,1, 0,0,1,1, 
				0,0,1,1, 0,0,1,1, 
				0,0,1,1, 0,0,1,1};
		gInfo.setColors(vc);
		gInfo.setColorIndices(secC);
				
		//ASIGNAR APARIENCIA
		Appearance appearance = new Appearance();
		appearance.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL,PolygonAttributes.CULL_NONE,0));
		Shape3D shape = new Shape3D(gInfo.getGeometryArray(),appearance);
		
		//PROCESO DE ROTACIÓN AUTOMÁTICA
		TransformGroup tGroup = new TransformGroup();
		tGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha time = new Alpha(-1, 1500);
		RotationInterpolator rotation = new RotationInterpolator(time, tGroup);
		BoundingSphere limit = new BoundingSphere();
		rotation.setSchedulingBounds(limit);
		tGroup.addChild(rotation);
		tGroup.addChild(shape);
		
		root.addChild(tGroup);
		root.addChild(bg);
	}
	
	//	CREAR ESCENA DONDE SE CONSTRUYE LA FIGURA CON TODAS SUS PROPIEDADES
	public BranchGroup createScene() {
		BranchGroup root = new BranchGroup();
		
		setFigure(root);
		
		return root;
	}
	
	//	CLASE PRNCIPAL PARA CORRER EL PROGRAMA
	public static void main(String[] args) {
		new Letter3D();
	}
}
