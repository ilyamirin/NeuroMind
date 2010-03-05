package perceptron.absract;

import com.thoughtworks.xstream.XStream;

public abstract class AbstractPerceptron<NType, IOWType> {

    private Class<NType>[] neurons;

    private Class<IOWType>[] v;

    XStream xstream = new XStream();

}
