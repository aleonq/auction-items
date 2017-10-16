package yours.auction.mobile.ani.net.ta.auctionyours.eventbus;

import java.util.List;

/**
 * Created by taru on 5/17/2017.
 */

public interface ListEvent<T> {
    void setList(List<T> list);

    List<T> getList();
}
