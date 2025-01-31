import javax.swing.*;
import java.awt.*;

public class JbrainTetris extends JTetris {

    DefaultBrain defbrain;
    int pieceCount;
    JCheckBox brainMode;
    Brain.Move bstMove;
    Brain.Move worstMove;
    JSlider adversary;
    JLabel statusLabel;

    JbrainTetris(int pixel) {
        super(pixel);
        pieceCount = 0;
        defbrain = new DefaultBrain();
    }
    @Override
    public Piece pickNextPiece() {
        int rnd = random.nextInt(1, 99);
        double worstScore = 0;
        Piece worstPiece = super.pickNextPiece();
        statusLabel.setText("ok");
        if(rnd < adversary.getValue()){
            statusLabel.setText("*ok*");
            for(int i = 0; i < super.pieces.length; i++){
                worstMove = defbrain.bestMove(super.board, super.pieces[i], super.board.getHeight(), worstMove);
                if(worstMove != null && worstScore < worstMove.score){
                    worstPiece = super.pieces[i];
                    worstScore = worstMove.score;
                }
            }
        }
        return worstPiece;
    }

@Override
public void tick(int verb) {
    if(brainMode.isSelected()) {
        if (verb == DOWN) {
            if(pieceCount != super.count) {
                pieceCount = super.count;
                super.board.undo();
                bstMove = defbrain.bestMove(super.board, super.currentPiece, super.board.getHeight() - TOP_SPACE, bstMove);
            }
            if(bstMove != null) {
                if (!bstMove.piece.equals(super.currentPiece)) super.tick(ROTATE);
                if (bstMove.x < super.currentX) super.tick(LEFT);
                else if (bstMove.x > super.currentX) super.tick(RIGHT);
            }
        }
    }
    super.tick(verb);
}

    @Override
    public JComponent createControlPanel(){
        JComponent panel = super.createControlPanel();
        panel.add(new JLabel("Brain:"));
        brainMode = new JCheckBox("Brain active");
        panel.add(brainMode);

        JPanel little = new JPanel();
        little.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0);
        adversary.setPreferredSize(new Dimension(100, 15));
        little.add(adversary);
        panel.add(little);

        statusLabel = new JLabel("ok");
        panel.add(statusLabel);

        return panel;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JbrainTetris tetris = new JbrainTetris(16);
        JFrame frame = JbrainTetris.createFrame(tetris);
        frame.setVisible(true);
    }

}

