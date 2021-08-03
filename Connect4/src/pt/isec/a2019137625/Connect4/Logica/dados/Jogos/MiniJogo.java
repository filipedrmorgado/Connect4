package pt.isec.a2019137625.Connect4.Logica.dados.Jogos;

import java.io.Serial;
import java.io.Serializable;

public abstract class MiniJogo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected int nrAcertos;
    protected long startTime = System.currentTimeMillis();
    protected long elapsedTime = 0L;
    protected boolean acertou;
    protected int chegouAoFim = 1;

    public abstract String getPergunta();
    public abstract void confirmaResposta(String resposta);
    public abstract int chegouAoFim();
    public abstract String getEstadoJogo();
    public boolean getInfoAcertou(){ return acertou;}
    public abstract long getTempoAcerto();

}
