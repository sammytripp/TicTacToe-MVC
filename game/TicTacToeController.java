import org.xml.sax.helpers.DefaultHandler;

public class TicTacToeController extends DefaultHandler {

    private TicTacToeModel model;
    private TicTacToeView view;

    public TicTacToeController(TicTacToeModel model){
        this.model = model;
        this.view = new TicTacToeView(model, model.getSize());
    }
}
