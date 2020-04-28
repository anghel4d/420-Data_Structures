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

        // Store all sections in an ArrayList to be interpreted by the Report.printAllSections() method.
        List<Section> sections = new ArrayList<>();
        // Current working Section
        Section currentSection = null;
        // Keep track of the start time for a section so we can calculate total elapsed and % at the end of it.
        long startTime = 0;
        // The regions that have been fully added to the section.
        Map<String, Region> currentRegions = null;
        // The regions that have been created and are waiting to be added to the section.
        Stack<Mark> pendingRegionTags = new Stack<>();

        for(Mark currentMark : marks){
            switch(currentMark.type){
                case START_SECTION: {
                    startTime = currentMark.time;
                    currentSection = new Section(currentMark.label);
                    currentRegions = currentSection.getRegions();
                    break;
                }
                case END_SECTION: {
                    // Indicate that the section was successfully run,
                    currentRegions.get("TOTAL").addRun();

                    // Calculate the final time spent in the section.
                    long endTime = currentMark.time;
                    long totalElapsed = endTime - startTime;
                    // Update the section's TOTAL region so that it displays properly at the end.
                    currentRegions.get("TOTAL").addElapsedTime(totalElapsed);

                    // Calculate percentages for each Region of the Segment for nice display and comparisons.
                    for(Map.Entry<String, Region> regionEntry : currentRegions.entrySet()){
                        Region tmp = regionEntry.getValue();
                        long regionTime = tmp.getElapsedTime();
                        tmp.setPercentOfSection((double)regionTime / totalElapsed);
                    }

                    // Add the section to the list
                    sections.add(currentSection);
                    break;
                }
                case START_REGION: {
                    // Creates a marker for a region to be added as soon as its END_REGION is found.
                    pendingRegionTags.push(currentMark);
                    break;
                }
                case END_REGION: {
                    // In the case of a section being returned to after its first END_REGION marker,
                    // that region must have its information (time and run) updated.
                    // Care is taken to ensure that a new region is not erroneously added in this case.
                    Mark popped = pendingRegionTags.pop();
                    if(currentRegions.containsKey(popped.label)) {
                        Region temp = currentRegions.get(popped.label);
                        temp.addRun();
                        temp.addElapsedTime((currentMark.time - popped.time));
                    }
                    else {
                        Region temp = new Region(currentSection, 1, (currentMark.time - popped.time), 0);
                        currentSection.addRegion(popped.label, temp);
                    }
                    //currentRegions = currentSection.getRegions();
                    break;
                }
            }
        }

        marks.clear();
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