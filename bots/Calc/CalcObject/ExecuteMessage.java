package bots.Calc.CalcObject;

import penguin_game.IceBuilding;

public class ExecuteMessage {
    private String info;
    private IceBuilding iceberg;
    
    public ExecuteMessage(String info, IceBuilding iceberg) {
        this.info = info;
        this.iceberg = iceberg;
    }
    public ExecuteMessage(String info) {
        this.info = info;
        this.iceberg = null;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public IceBuilding getIceberg() {
        if(iceberg == null){
            return null;
        }
        return iceberg;
    }
    public void setIceberg(IceBuilding iceberg) {
        this.iceberg = iceberg;
    }
    @Override
    public String toString() {
        return "ExecuteMessage [info=" + info + ", iceberg=" + iceberg + "]";
    }
}
