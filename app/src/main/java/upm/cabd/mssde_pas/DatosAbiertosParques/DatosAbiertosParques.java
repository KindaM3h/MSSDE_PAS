
package upm.cabd.mssde_pas.DatosAbiertosParques;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosAbiertosParques {

    @SerializedName("@context")
    @Expose
    private QueryContext context;
    @SerializedName("@graph")
    @Expose
    private List<Graph> graph;

    public QueryContext getContext() {
        return context;
    }

    public void setContext(QueryContext context) {
        this.context = context;
    }

    public List<Graph> getGraph() {
        return graph;
    }

    public void setGraph(List<Graph> graph) {
        this.graph = graph;
    }

}
