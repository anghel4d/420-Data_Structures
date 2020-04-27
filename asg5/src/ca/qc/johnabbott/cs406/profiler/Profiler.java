package ca.qc.johnabbott.cs406.profiler;

import java.util.*;

/**
 * A simple profiling class.
 */
public class Profiler {

    /*
    Delimits a section or region of the profiling.
    */
    private static class Mark {

        // stores the type of mark
        private enum Type {
            START_REGION, END_REGION, START_SECTION, END_SECTION
        }

        public Type type;
        public long time;
        public String label;

        // Create mark without a label
        public Mark(Type type, long time) {
            this(type, time, null);
        }

        // Create a mark with a label
        public Mark(Type type, long time, String label) {
            this.type = type;
            this.time = time;
            this.label = label;
        }

        @Override
        public String toString() {
            return "Mark{" +
                    "type=" + type +
                    ", time=" + time +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

    // store singleton instance
    private static Profiler INSTANCE;
    static {
        INSTANCE = new Profiler();
    }

    /**
     * Get profiler singleton instance.
     * @return the profiler singleton.
     */
    public static Profiler getInstance() {
        return INSTANCE;
    }

    // store marks in list
    private List<Mark> marks;

    // use to prevent regions when not wanted/needed.
    private boolean paused;
    private boolean inSection;

    // private constructor for singleton
    private Profiler() {
        // linked list, because append is a constant time operation.
        marks = new LinkedList<>();
        paused = false;
        inSection = false;
    }

    /**
     * Starts a new profiling section.
     * @param label The section label.
     */
    public void startSection(String label) {
        if(!paused) {
            marks.add(new Mark(Mark.Type.START_SECTION, System.nanoTime(), label));
            inSection = true;
        }
    }

    /**
     * Ends a section. Must be paired with a corresponding call to `startSection(..)`.
     */
    public void endSection() {
        if(!paused) {
            marks.add(new Mark(Mark.Type.END_SECTION, System.nanoTime()));
            inSection = false;
        }
    }

    /**
     * Starts a new profiling region.
     * @param label The region label.
     */
    public void startRegion(String label) {
        if(!paused && inSection)
            marks.add(new Mark(Mark.Type.START_REGION, System.nanoTime(), label));
    }

    /**
     * Ends a region. Must be paired with a corresponding call to `startRegion(..)`.
     */
    public void endRegion() {
        if(!paused && inSection)
            marks.add(new Mark(Mark.Type.END_REGION, System.nanoTime()));
    }

    /**
     * Using the currently collected data, generate all the section data for reporting.
     * @return A list of sections.
     */
    public List<Section> produceSections() {
        List<Section> sections = new ArrayList<>();
        // TODO: compile results
        return sections;
    }


    @Override
    public String toString() {
        // print all marks using formatting.
        StringBuilder builder = new StringBuilder();
        for(Mark mark : marks)
            builder.append(String.format("%d %13s %-30s\n", mark.time, mark.type.toString(), mark.label != null ? mark.label : ""));
        return builder.toString();
    }
}