package mapper;

import model.Counter;
import model.LineModel;

public class ToModelMapper {

    public static LineModel toModel(String[] lines) {
        LineModel result = new LineModel();
        result.setNumber(lines[0]);
        result.setName(lines[1]);
        result.setAddress(lines[2]);
        result.setPeriod(lines[3]);
        result.setSum(lines[4]);
        for (int i = 5; i < lines.length; i += 2) {
            result.getCounters()
                    .add(new Counter(lines[i], lines[i + 1]));
        }
        return result;
    }
}
