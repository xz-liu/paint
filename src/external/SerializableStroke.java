package external;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Native;

public class SerializableStroke implements java.io.Serializable,Stroke{
    public transient BasicStroke theDamnBasicStroke;
    public SerializableStroke(float width, int cap, int join, float miterlimit,
                              float dash[], float dash_phase) {
        theDamnBasicStroke=new BasicStroke(width,cap,join,miterlimit,dash,dash_phase);
    }
//    float width, int cap, int join, float miterlimit,
//    float dash[], float dash_phase)
    private void writeObject(ObjectOutputStream out) throws IOException{
        out.writeFloat(theDamnBasicStroke.getLineWidth());
        out.writeInt(theDamnBasicStroke.getEndCap());
        out.writeInt(theDamnBasicStroke.getLineJoin());
        out.writeFloat(theDamnBasicStroke.getMiterLimit());
        out.writeObject(theDamnBasicStroke.getDashArray());
        out.writeFloat(theDamnBasicStroke.getDashPhase());
    }

    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException{
        float width=in.readFloat();
        int cap=in.readInt(); int join=in.readInt();
        float miterlimit=in.readFloat();
        float dash[]=(float[])in.readObject();
        float dash_phase=in.readFloat();
        theDamnBasicStroke=new BasicStroke(width,cap,join,miterlimit,dash,dash_phase);
    }
    /**
     * Constructs a solid {@code BasicStroke} with the specified
     * attributes.
     * @param width the width of the {@code BasicStroke}
     * @param cap the decoration of the ends of a {@code BasicStroke}
     * @param join the decoration applied where path segments meet
     * @param miterlimit the limit to trim the miter join
     * @throws IllegalArgumentException if {@code width} is negative
     * @throws IllegalArgumentException if {@code cap} is not either
     *         CAP_BUTT, CAP_ROUND or CAP_SQUARE
     * @throws IllegalArgumentException if {@code miterlimit} is less
     *         than 1 and {@code join} is JOIN_MITER
     * @throws IllegalArgumentException if {@code join} is not
     *         either JOIN_ROUND, JOIN_BEVEL, or JOIN_MITER
     */
    public SerializableStroke(float width, int cap, int join, float miterlimit) {
        this(width, cap, join, miterlimit, null, 0.0f);
    }

    /**
     * Constructs a solid {@code BasicStroke} with the specified
     * attributes.  The {@code miterlimit} parameter is
     * unnecessary in cases where the default is allowable or the
     * line joins are not specified as JOIN_MITER.
     * @param width the width of the {@code BasicStroke}
     * @param cap the decoration of the ends of a {@code BasicStroke}
     * @param join the decoration applied where path segments meet
     * @throws IllegalArgumentException if {@code width} is negative
     * @throws IllegalArgumentException if {@code cap} is not either
     *         CAP_BUTT, CAP_ROUND or CAP_SQUARE
     * @throws IllegalArgumentException if {@code join} is not
     *         either JOIN_ROUND, JOIN_BEVEL, or JOIN_MITER
     */
    public SerializableStroke(float width, int cap, int join) {
        this(width, cap, join, 10.0f, null, 0.0f);
    }

    /**
     * Constructs a solid {@code BasicStroke} with the specified
     * line width and with default values for the cap and join
     * styles.
     * @param width the width of the {@code BasicStroke}
     * @throws IllegalArgumentException if {@code width} is negative
     */
    public SerializableStroke(float width) {
        this(width, CAP_SQUARE, JOIN_MITER, 10.0f, null, 0.0f);
    }

    /**
     * Constructs a new {@code BasicStroke} with defaults for all
     * attributes.
     * The default attributes are a solid line of width 1.0, CAP_SQUARE,
     * JOIN_MITER, a miter limit of 10.0.
     */
    public SerializableStroke() {
        this(1.0f, CAP_SQUARE, JOIN_MITER, 10.0f, null, 0.0f);
    }
    /**
     * Joins path segments by extending their outside edges until
     * they meet.
     */
    @Native
    public static final int JOIN_MITER = 0;

    /**
     * Joins path segments by rounding off the corner at a radius
     * of half the line width.
     */
    @Native public static final int JOIN_ROUND = 1;

    /**
     * Joins path segments by connecting the outer corners of their
     * wide outlines with a straight segment.
     */
    @Native public static final int JOIN_BEVEL = 2;

    /**
     * Ends unclosed subpaths and dash segments with no added
     * decoration.
     */
    @Native public static final int CAP_BUTT = 0;

    /**
     * Ends unclosed subpaths and dash segments with a round
     * decoration that has a radius equal to half of the width
     * of the pen.
     */
    @Native public static final int CAP_ROUND = 1;

    /**
     * Ends unclosed subpaths and dash segments with a square
     * projection that extends beyond the end of the segment
     * to a distance equal to half of the line width.
     */
    @Native public static final int CAP_SQUARE = 2;

    @Override
    public Shape createStrokedShape(Shape p) {
        return theDamnBasicStroke.createStrokedShape(p);
    }
}

