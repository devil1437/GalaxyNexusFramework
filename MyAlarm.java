
package android.app;

import android.app.PendingIntent;
import	android.util.TimeUtils;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.PrintWriter;

public class MyAlarm implements Parcelable
{
        public int type;
        public int count;
        public long when;
        public long repeatInterval;
        public PendingIntent operation;
        
        public MyAlarm(Parcel in) {
            readFromParcel(in);
        }
       
        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(type);
            dest.writeInt(count);
            dest.writeLong(when);
            dest.writeLong(repeatInterval);
            dest.writeParcelable(operation, flags);
        }

        private void readFromParcel(Parcel in) {
            type = in.readInt();
            count = in.readInt();
            when = in.readLong();
            repeatInterval = in.readLong();
            operation = (PendingIntent) in.readParcelable(PendingIntent.class.getClassLoader());
        }

        public static final Parcelable.Creator<MyAlarm> CREATOR = new Parcelable.Creator<MyAlarm>() {
            public MyAlarm createFromParcel(Parcel in) {
                return new MyAlarm(in);
            }
            public MyAlarm[] newArray(int size) {
                return new MyAlarm[size];
            }
        };
        
        public MyAlarm() {
            when = 0;
            repeatInterval = 0;
            operation = null;
        }
        
        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Alarm{ ");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" type ");
            sb.append(type);
            sb.append(" creatorPackage ");
            sb.append(operation.getCreatorPackage());
            sb.append(" creatorUid ");
            sb.append(operation.getCreatorUid());
			sb.append(" creatorUid ");
            sb.append(operation.getCreatorUid());
            sb.append(" }");
            return sb.toString();
        }

        public void dump(PrintWriter pw, String prefix, long now) {
            pw.print(prefix); pw.print("type="); pw.print(type);
                    pw.print(" when="); TimeUtils.formatDuration(when, now, pw);
                    pw.print(" repeatInterval="); pw.print(repeatInterval);
                    pw.print(" count="); pw.println(count);
            pw.print(prefix); pw.print("operation="); pw.println(operation);
        }
}