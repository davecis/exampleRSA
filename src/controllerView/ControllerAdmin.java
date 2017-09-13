package controllerView;
//elemetos para manipular los elementos de la vista
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
//elementos de vista del fxml
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
//parametros de la interfaz
import java.net.URL;
import java.util.ResourceBundle;
//elementos de base de datos
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import model.Auscultacion;
import model.Funcionario;
import model.Opcion;
import model.Participante;

public class ControllerAdmin implements Initializable{

	//DB base = new DB();
	ResultSet rs;

	@FXML private TextField innomausc;
	@FXML private TextArea indesausc;
	@FXML private DatePicker infechfinausc;
	@FXML private DatePicker infechiniausc;

    @FXML private TableView<Auscultacion> tablaAuscultaciones;
    @FXML private TableColumn tnomausc;
    @FXML private TableColumn tdesausc;
    @FXML private TableColumn tfechiniausc;
    @FXML private TableColumn tfechfinausc;
    ObservableList<Auscultacion> auscultaciones;
    //private int postablaAuscultacion;

    @FXML private TextField innomparti;
	@FXML private TextField inapepatparti;
	@FXML private TextField inapematparti;
	@FXML private TextField inmatriparti;

    @FXML private TableView<Participante> tablaParticipantes;
    @FXML private TableColumn tnomparti;
    @FXML private TableColumn tapepatparti;
    @FXML private TableColumn tapematparti;
    @FXML private TableColumn tmatriparti;
    ObservableList<Participante> participantes;

    @FXML private TextField innomfunc;
	@FXML private TextField inapepatfunc;
	@FXML private TextField inapematfunc;
	@FXML private TextField inusufunc;

    @FXML private TableView<Funcionario> tablaFuncionarios;
    @FXML private TableColumn tnomfunc;
    @FXML private TableColumn tapepatfunc;
    @FXML private TableColumn tapematfunc;
    @FXML private TableColumn tusufunc;
    ObservableList<Funcionario> funcionarios;

    @FXML private TextField innomopcion;
	@FXML private TextArea indesopcion;

    @FXML private TableView<Opcion> tablaOpciones;
    @FXML private TableColumn tnomopcion;
    @FXML private TableColumn tdesopcion;
    @FXML private TableColumn taccopcion;
    ObservableList<Opcion> opciones;

    @FXML private void addAuscultacion(ActionEvent event){
    	Auscultacion auscultacion = new Auscultacion();
    	auscultacion.nombre.set(innomausc.getText());
    	auscultacion.descripcion.set(indesausc.getText());
    	auscultacion.fechafin.set(infechfinausc.getValue().toString());
    	auscultacion.fechainicio.set(infechiniausc.getValue().toString());
    	auscultaciones.add(auscultacion);
    }

    @FXML private void addParticipante(ActionEvent event){
    	Participante participante = new Participante();
    	participante.nombre.set(innomparti.getText());
    	participante.apellidopaterno.set(inapepatparti.getText());
    	participante.apellidomaterno.set(inapematparti.getText());
    	participante.matricula.set(inmatriparti.getText());
    	participantes.add(participante);
    }

    @FXML private void addFuncionario(ActionEvent event){
    	Funcionario funcionario = new Funcionario();
    	funcionario.nombre.set(innomfunc.getText());
    	funcionario.apellidopaterno.set(inapepatfunc.getText());
    	funcionario.apellidomaterno.set(inapematfunc.getText());
    	funcionario.usuario.set(inusufunc.getText());
    	funcionarios.add(funcionario);
    }

    @FXML private void addOpcion(ActionEvent event){
    	Opcion opcion = new Opcion();
    	opcion.nombre.set(innomopcion.getText());
    	opcion.descripcion.set(indesopcion.getText());
    	opciones.add(opcion);
    }

    public void inicializaTabla(){
        tablaAuscultaciones.setEditable(true);
        tnomausc.setCellValueFactory(new PropertyValueFactory<Auscultacion, String>("nombre"));
        tnomausc.setCellFactory(TextFieldTableCell.forTableColumn());
        tnomausc.setOnEditCommit(new EventHandler<CellEditEvent>() {
            @Override
            public void handle(CellEditEvent t) {
                ((Auscultacion) t.getTableView().getItems().get(t.getTablePosition().getRow())).nombre.set(t.getNewValue().toString());
            }
        });
        tdesausc.setCellValueFactory(new PropertyValueFactory<Auscultacion, String>("descripcion"));
        tfechiniausc.setCellValueFactory(new PropertyValueFactory<Auscultacion, String>("fechainicio"));
        tfechfinausc.setCellValueFactory(new PropertyValueFactory<Auscultacion, String>("fechafin"));

        auscultaciones = FXCollections.observableArrayList();
        tablaAuscultaciones.setItems(auscultaciones);

        tnomparti.setCellValueFactory(new PropertyValueFactory<Participante, String>("nombre"));
        tapepatparti.setCellValueFactory(new PropertyValueFactory<Participante, String>("apellidopaterno"));
        tapematparti.setCellValueFactory(new PropertyValueFactory<Participante, String>("apellidomaterno"));
        tmatriparti.setCellValueFactory(new PropertyValueFactory<Participante, String>("matricula"));

        participantes = FXCollections.observableArrayList();
        tablaParticipantes.setItems(participantes);

        tnomfunc.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nombre"));
        tapepatfunc.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("apellidopaterno"));
        tapematfunc.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("apellidomaterno"));
        tusufunc.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("usuario"));

        funcionarios = FXCollections.observableArrayList();
        tablaFuncionarios.setItems(funcionarios);

        tnomopcion.setCellValueFactory(new PropertyValueFactory<Opcion, String>("nombre"));
        tdesopcion.setCellValueFactory(new PropertyValueFactory<Opcion, String>("descripcion"));

        opciones = FXCollections.observableArrayList();
        tablaOpciones.setItems(opciones);
    }

    public void llenaTablaBase() {
    	//base.setConnection("mysql-connector-java-3.1.13-bin.jar","jdbc:mysql://localhost/ide");
    	for (int i = 0; i < 20; i++) {
            Auscultacion a1 = new Auscultacion();
            Participante p1 = new Participante();
            Funcionario f1 = new Funcionario();
            Opcion o1 = new Opcion();
            a1.nombre.set("Nombre");
            a1.descripcion.set("Apellido ");
            a1.fechainicio.set("fechainicio");
            a1.fechafin.set("fechafin");
            p1.nombre.set("Nombre");
            p1.apellidopaterno.set("apellidopaterno");
            p1.apellidomaterno.set("apellidomaterno");
            p1.matricula.set("matricula");
            f1.nombre.set("Nombre");
            f1.apellidopaterno.set("apellidopaterno");
            f1.apellidomaterno.set("apellidomaterno");
            f1.usuario.set("matricula");
            o1.nombre.set("apellidomaterno");
            o1.descripcion.set("matricula");
            /*
            a1.nombre.set("Nombre");
            a1.descripcion.set("Apellido ");
            a1.fechainicio.set("fechainicio");
            a1.fechafin.set("fechafin");
            */
            auscultaciones.add(a1);
            participantes.add(p1);
            funcionarios.add(f1);
            opciones.add(o1);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.inicializaTabla();
        //try{
        	this.llenaTablaBase();
        //}catch(SQLException e){System.out.println("Error al ejecutar una consulta");}
        //catch(IOException e){System.out.println("Error al cargar la base de datos");}
    }
}