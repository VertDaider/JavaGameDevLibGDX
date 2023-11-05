package ru.serioussem.wander.game.constants;

public enum TypeCell {
    GREEN("green", "Зеленый"),
    BLUE("blue", "Синий"),
    YELLOW("yellow", "Желтый"),
    RED("red", "Красный"),
    WHITE("white", "Белый");

    private final String type;
    private final String rusName;

    TypeCell(String type, String rusName) {
        this.type = type;
        this.rusName = rusName;
    }

    static public TypeCell getByType(String type) throws IllegalArgumentException {
        for (TypeCell cell : values()) {
            if (type.equals(cell.type)) {
                return cell;
            }
        }
        throw new IllegalArgumentException("Неизвестный TypeCell: " + type);
    }

    public String getType() {
        return type;
    }

    public String getRusName() {return rusName;};

    @Override
    public String toString() {
        return "TypeCell{" +
                "type='" + type + '\'' +
                ", rusName='" + rusName + '\'' +
                '}';
    }
}
